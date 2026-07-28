package com.reshmah.incident.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MatchResult {
    private Long knowledgeEntryId;
    private String title;
    private double similarityScore;
    private String resolutionNotes;
    private String rootCause;
}