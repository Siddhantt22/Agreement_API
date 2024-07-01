package com.example.AgreementPractice.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FetchDocumentDTO {


    private Integer expireInDays;

    private Boolean generateAccessToken;

    private String displayOnPage;

    private Boolean sendSignLink;

    private Boolean notifySigners;

    private String fileName;

    private String fileData;

    private String signedData;

    private  Long  noOfPages;

    private String identifier;

    private String name;

    private String email;



}
