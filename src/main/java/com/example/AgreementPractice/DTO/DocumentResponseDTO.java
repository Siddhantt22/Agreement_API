package com.example.AgreementPractice.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DocumentResponseDTO {


    private String identifier;

    private String name;

    private String email;

    private String status;

    private String fileName;

    private String fileData;

    private String signedData;

}
