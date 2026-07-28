package com.reshmah.incident.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class AIAnalysisResponse {

    private IncidentResponse incident;

    private TriageResult.ClassificationDecision classification;

    private List<MatchResult> similarIncidents;

    private String rootCause;

    private String aiSummary;

}
