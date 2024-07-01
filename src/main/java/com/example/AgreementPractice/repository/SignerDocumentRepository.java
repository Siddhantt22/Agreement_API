package com.example.AgreementPractice.repository;

import com.example.AgreementPractice.DTO.DocumentResponseDTO;

import com.example.AgreementPractice.DTO.FetchDocumentDTO;
import com.example.AgreementPractice.entity.SignerDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SignerDocumentRepository extends JpaRepository<SignerDocument,Long> {

    @Query("SELECT new com.example.AgreementPractice.DTO.DocumentResponseDTO(s.identifier,s.name,s.email,s.status,sd.fileName,sd.fileData,sd.signedData) FROM SignerDocument sd Left Join sd.signers s")
    List<DocumentResponseDTO> findAllDocuments();

    @Query("SELECT new com.example.AgreementPractice.DTO.DocumentResponseDTO(s.identifier,s.name,s.email,sd.status,sd.fileName,sd.fileData,sd.signedData) FROM SignerDocument sd Left Join sd.signers s WHERE sd.id= :id ")
    List<DocumentResponseDTO> findBydocId(Long id);

    @Query("SELECT new com.example.AgreementPractice.DTO.FetchDocumentDTO(sd.expireInDays,sd.generateAccessToken,sd.displayOnPage,sd.sendSignLink,sd.notifySigners,sd.fileName,sd.fileData,sd.signedData,sd.noOfPages,s.identifier,s.name,s.email) FROM SignerDocument sd Left Join sd.signers s WHERE sd.id= :id ")
    List<FetchDocumentDTO> FetchFullDocuemnt(Long id);
}