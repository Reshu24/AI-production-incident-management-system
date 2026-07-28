package com.reshmah.incident.ai;

import lombok.Data;

@Data
public class OllamaIncidentDTO {
    private String title;

    private String description;

    private String severity;

    private String affectedService;

    private String category;

    private String rootCause;

    private String aiSummary;
}
