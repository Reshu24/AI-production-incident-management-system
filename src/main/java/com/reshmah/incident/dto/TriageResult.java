package com.reshmah.incident.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class TriageResult {

    // the incident that was just created
    private IncidentResponse incident;

    // top 5 similar past incidents from knowledge base
    private List<MatchResult> similarIncidents;

    // the routing decision
    private ClassificationDecision classification;

    @Data
    @AllArgsConstructor
    public static class ClassificationDecision {
        private String category;            // "Payments"
        private String assignedTeam;        // "Payments Team"
        private String contactEmail;        // "payments@company.com"
        private double confidenceScore;     // 0.66
        private boolean isAutoAssigned;     // false
        private String matchedKeywords;     // "payment, checkout, timeout"
        private String decision;            // "AUTO ASSIGNED" or "SUGGESTED"
    }
}