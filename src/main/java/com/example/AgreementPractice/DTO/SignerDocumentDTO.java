package com.example.AgreementPractice.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class SignerDocumentDTO {

    @JsonProperty("expire_in_days")
    private Integer expireInDays;
    @JsonProperty("generate_access_token")
    private Boolean generateAccessToken;
    @JsonProperty("display_on_page")
    private String displayOnPage;
    @JsonProperty("send_sign_link")
    private Boolean sendSignLink;
    @JsonProperty("notify_signers")
    private Boolean notifySigners;
    @JsonProperty("file_name")
    private String fileName;
    @JsonProperty("unsigned_data")
    private String fileData;
    @JsonProperty("signed_data")
    private String signedData;
    @JsonProperty( "no_of_pages")
    private  Long  noOfPages;
    @NotEmpty(message = "signers details cannot be empty")
    @Valid
    private List<SignersRequestDTO> signers;

}
