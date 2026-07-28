package com.reshmah.incident.dto;

import com.reshmah.incident.entity.OwnershipEntity;
import lombok.Data;

@Data
public class OwnershipMappingResponse {

    private Long id;
    private String category;
    private String teamName;
    private String contactEmail;

    // matching your exact pattern from IncidentResponse + KnowledgeEntryResponse
    public static OwnershipMappingResponse responseFromEntity(OwnershipEntity mapping) {
        OwnershipMappingResponse response = new OwnershipMappingResponse();
        response.setId(mapping.getId());
        response.setCategory(mapping.getCategory());
        response.setTeamName(mapping.getTeamName());
        response.setContactEmail(mapping.getContactEmail());
        return response;
    }
}