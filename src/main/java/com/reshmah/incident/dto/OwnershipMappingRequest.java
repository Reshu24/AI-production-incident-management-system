package com.reshmah.incident.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OwnershipMappingRequest {

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Team name is required")
    private String teamName;

    @NotBlank(message = "Contact email is required")
    @Email(message = "Contact email must be valid")
    private String contactEmail;
}