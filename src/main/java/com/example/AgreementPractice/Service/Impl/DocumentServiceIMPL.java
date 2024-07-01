package com.example.AgreementPractice.Service.Impl;


import com.example.AgreementPractice.DTO.*;

import com.example.AgreementPractice.Service.DocumentService;
import com.example.AgreementPractice.entity.SignerDetails;
import com.example.AgreementPractice.entity.SignerDocument;
import com.example.AgreementPractice.exception.EmptyInputException;
import com.example.AgreementPractice.repository.SignerDetailsRepository;
import com.example.AgreementPractice.repository.SignerDocumentRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class DocumentServiceIMPL implements DocumentService {
    @Autowired
    private SignerDocumentRepository repo;

    @Autowired
    private SignerDetailsRepository detailsRepository;

    @Value("${document.secret}")
    private String documentSecret;

    @Override
    public String UploadDocument(SignerDocumentDTO documentDTO) {
        if(documentDTO.getFileData().isEmpty() || documentDTO.getFileData().length()==0)
        {
            throw new EmptyInputException("601","Input field is empty");
        }
       if( Objects.nonNull(documentDTO) && StringUtils.hasText(documentDTO.getFileData()))
        {
            SignerDocument document=new SignerDocument();
            document.setNoOfPages(documentDTO.getNoOfPages());
            document.setDisplayOnPage(documentDTO.getDisplayOnPage());
            document.setFileName(documentDTO.getFileName());
            document.setFileData(documentDTO.getFileData());
            document.setExpireInDays(documentDTO.getExpireInDays());
            document.setGenerateAccessToken(documentDTO.getGenerateAccessToken());
            document.setNotifySigners(documentDTO.getNotifySigners());
            document.setSendSignLink(documentDTO.getSendSignLink());
            document.setStatus("Unsigned");

            document= repo.save(document);
            saveDocumentDetail(document,documentDTO);
            return String.valueOf(document.getId());
        }

        return null;
    }

    @Override
    public List<DocumentResponseDTO> fetchDocument() {
        return repo.findAllDocuments();
    }

    @Override
    public List<DocumentResponseDTO> fetchIndDoc(Long id) {

       return repo.findBydocId(id);


    }

    @Override
    public String signDocument(SignDocumentRequestDTO documentRequestDTO)  {

        if (!documentRequestDTO.getOtp().equals(documentSecret)) {
            return "Wrong OTP!!!";
        }


        Optional<SignerDocument> optionalDocument = repo.findById(documentRequestDTO.getDocId());
        if (optionalDocument.isEmpty()) {
            return "Document not found";
        }
        List<DocumentResponseDTO> documentOptional = repo.findBydocId(documentRequestDTO.getDocId());
        SignerDocument document = optionalDocument.get();


        document.setStatus("signed");
        List<SignerDetails> details=document.getSigners();
        for (SignerDetails detail : details) {
            detail.setStatus("signed");
        }



        try {

            String signedPdfBase64 = addWatermarkToPdf(document.getFileData(),document.getSigners().get(0).getName());
            document.setSignedData(signedPdfBase64);

            // Save the updated document
            repo.save(document);

            return "Signed Successfully!";
        } catch (IOException e) {
            return "Error adding watermark: " + e.getMessage();
        }
    }

    @Override
    public List<FetchDocumentDTO> FetchFullDocument(Long id) {
        return repo.FetchFullDocuemnt(id);
    }

    @Override
    public ByteArrayInputStream convertToPdf(String base64Pdf) {

            if (base64Pdf == null) {
                logger.error("Base64 PDF string is null");
                throw new IllegalArgumentException("Base64 PDF string must not be null");
            }

            logger.info("Decoding Base64 PDF string of length: " + base64Pdf.length());
            byte[] pdfBytes = Base64.getDecoder().decode(base64Pdf);
            logger.info("Decoded PDF bytes with length: " + pdfBytes.length);
            return new ByteArrayInputStream(pdfBytes);
        }

    @Override
    public ResponseEntity<?> download(Long id) {
                    List<DocumentResponseDTO> dto=repo.findBydocId(id);
           DocumentResponseDTO document=dto.get(0);

         if (!document.getStatus().equals("signed")) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You are not signed yet.");
         }

        String signedData= document.getSignedData();

        ByteArrayInputStream byteArrayInputStream = convertToPdf(signedData);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=generated.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(byteArrayInputStream));
    }


    private String addWatermarkToPdf(String base64Pdf, String signerName) throws IOException {
        byte[] pdfBytes = Base64.getDecoder().decode(base64Pdf);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(pdfBytes);
        PDDocument pdfDocument = PDDocument.load(byteArrayInputStream);

        for (PDPage page : pdfDocument.getPages()) {
            PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, page, PDPageContentStream.AppendMode.APPEND, true, true);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.setNonStrokingColor(99, 99, 99); // Light gray color
            contentStream.setTextMatrix(1, 0, 0, 1  , 400, 30);
            contentStream.showText("Signed by: " + signerName);
            contentStream.endText();
            contentStream.close();
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        pdfDocument.save(outputStream);
        pdfDocument.close();

        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }
    private void saveDocumentDetail(SignerDocument document, SignerDocumentDTO signerDocumentRequestDTO) {
        List<SignerDetails> detailsList=new ArrayList<>();
        for(SignersRequestDTO requestDTO : signerDocumentRequestDTO.getSigners())
        {
            SignerDetails details=new SignerDetails();
            details.setIdentifier(requestDTO.getIdentifier());
            details.setEmail(requestDTO.getEmail());
            details.setSignType("ADHAAR");
            details.setName(requestDTO.getName());
            details.setReason(requestDTO.getReason());
            details.setStatus("Unsigned");
            details.setSignersDocuments(document);
            detailsList.add(details);
        }
        detailsRepository.saveAll(detailsList);

    }
}
