package com.reshmah.incident.dto;

import com.reshmah.incident.entity.IncidentEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class IncidentRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    private String affectedService;

    private IncidentEntity.Severity severity;
}
