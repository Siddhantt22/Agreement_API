package com.example.AgreementPractice.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@NoArgsConstructor
@Data

public class SignersRequestDTO {
    @JsonProperty("identifier")

    @NotNull(message = "identifier cannot be null")
    @NotBlank(message = "identifier is required")
    @Size(min = 3,message = "identifier should be greater or equals to 3")
    private String identifier;
    @JsonProperty("name")
    private String name;
    @JsonProperty("reason")
    private String reason;
    @JsonProperty("sign_type")
    private String signType;
    @JsonProperty("email_address")
    private String email;
    @JsonProperty("status")
    private String status;
}



