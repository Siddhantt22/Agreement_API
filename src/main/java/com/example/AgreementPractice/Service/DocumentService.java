package com.example.AgreementPractice.Service;

import com.example.AgreementPractice.DTO.DocumentResponseDTO;
import com.example.AgreementPractice.DTO.FetchDocumentDTO;
import com.example.AgreementPractice.DTO.SignDocumentRequestDTO;
import com.example.AgreementPractice.DTO.SignerDocumentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface DocumentService {


    String UploadDocument(SignerDocumentDTO documentDTO);

    List<DocumentResponseDTO> fetchDocument();



    List<DocumentResponseDTO> fetchIndDoc(Long id);

    static final Logger logger = LoggerFactory.getLogger(DocumentService.class);



    String signDocument(SignDocumentRequestDTO documentDTO);

    List<FetchDocumentDTO> FetchFullDocument(Long id);

    ByteArrayInputStream convertToPdf(String signedData);

    ResponseEntity<?> download(Long id);
}
