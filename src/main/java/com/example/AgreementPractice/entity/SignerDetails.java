package com.example.AgreementPractice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="signer_details")
public class SignerDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(name = "email_address")
    private String email;
    @Column(name = "status")
    private String status;
    @Column(name = "identifier")
    private String identifier;
    @Column(name = "name")
    private String name;
    @Column(name = "reason")
    private String reason;
    @Column(name = "sign_type")
    private String signType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "signer_documents_id")
    private SignerDocument signersDocuments;

}
