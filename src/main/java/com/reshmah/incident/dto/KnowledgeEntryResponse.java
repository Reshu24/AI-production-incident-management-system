package com.reshmah.incident.dto;

import com.reshmah.incident.entity.IncidentEntity;
import com.reshmah.incident.entity.KnowledgeEntry;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class KnowledgeEntryResponse {

    private Long id;
    private String title;
    private String description;
    private String affectedService;
    private IncidentEntity.Severity severity;
    private String rootCause;
    private String resolutionNotes;
    private LocalDateTime resolvedAt;

    // matching your exact pattern from IncidentResponse
    public static KnowledgeEntryResponse responseFromEntity(KnowledgeEntry entry) {
        KnowledgeEntryResponse response = new KnowledgeEntryResponse();
        response.setId(entry.getId());
        response.setTitle(entry.getTitle());
        response.setDescription(entry.getDescription());
        response.setAffectedService(entry.getAffectedService());
        response.setSeverity(entry.getSeverity());
        response.setRootCause(entry.getRootCause());
        response.setResolutionNotes(entry.getResolutionNotes());
        response.setResolvedAt(entry.getResolvedAt());
        return response;
    }
}
