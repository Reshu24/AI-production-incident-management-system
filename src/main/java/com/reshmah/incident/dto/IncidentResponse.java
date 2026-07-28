package com.reshmah.incident.dto;

import com.reshmah.incident.entity.IncidentEntity;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class IncidentResponse {
    private Long id;
    private String title;
    private String description;
    private String affectedService;
    private IncidentEntity.Severity severity;
    private LocalDateTime createdAt;

    public static IncidentResponse responseFromEntity(IncidentEntity incident) {
        IncidentResponse response = new IncidentResponse();
        response.setId(incident.getId());
        response.setTitle(incident.getTitle());
        response.setDescription(incident.getDescription());
        response.setAffectedService(incident.getAffectedService());
        response.setSeverity(incident.getSeverity());
        response.setCreatedAt(incident.getCreatedAt());
        return response;
    }
}