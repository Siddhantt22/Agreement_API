package com.example.AgreementPractice.Controller;

import com.example.AgreementPractice.DTO.DocumentResponseDTO;
import com.example.AgreementPractice.DTO.FetchDocumentDTO;
import com.example.AgreementPractice.DTO.SignDocumentRequestDTO;
import com.example.AgreementPractice.DTO.SignerDocumentDTO;
import com.example.AgreementPractice.Service.DocumentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController

public class SignerDocumentController {

    @Autowired
    private DocumentService service;



    @PostMapping("/uploadDocument")
    public ResponseEntity<String> uploadDocument( @RequestBody @Valid SignerDocumentDTO documentDTO) {
        String response = service.UploadDocument(documentDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/document")
    public List<DocumentResponseDTO> fetchDocuments() {
        return service.fetchDocument();
    }

    @GetMapping("/documents/{id}")
    public List<DocumentResponseDTO> fetchdoc(@PathVariable Long id) {
        return service.fetchIndDoc(id);
    }



    @PostMapping("/signDocument")
    public ResponseEntity<String> signDocument(@RequestBody SignDocumentRequestDTO documentRequestDTO) {


        String status = service.signDocument(documentRequestDTO);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @GetMapping("/fetchDocument/{id}")
    public List<FetchDocumentDTO> fetchIndDoc(@PathVariable Long id)
    {
        return service.FetchFullDocument(id);
    }

    @GetMapping("/document/download/{id}")
    public ResponseEntity<?> download(@PathVariable Long id) {


      return service.download(id);

    }


}



