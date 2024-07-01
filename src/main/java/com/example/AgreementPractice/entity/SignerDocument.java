package com.example.AgreementPractice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name="signer_documents")
public class SignerDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "status")
    private String status;
    @Column(name = "sign_type")
    private String signType;
    @Column(name= "expire_in_days")
    private Integer expireInDays;
    @Column(name="generate_access_token")
    private Boolean generateAccessToken;
    @Column(name="display_on_page")
    private String displayOnPage;
    @Column(name="send_sign_link")
    private Boolean sendSignLink;
    @Column(name="notify_signers")
    private Boolean notifySigners;
    @Column(name="file_name")
    private String fileName;
    @Column(name="unsigned_data")
    private String fileData;
    @Column(name="signed_data")
    private String signedData;
    @Column(name= "no_of_pages")
    private  Long  noOfPages;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "signersDocuments", cascade = CascadeType.ALL)
    private List<SignerDetails> signers = new ArrayList<>();
}
