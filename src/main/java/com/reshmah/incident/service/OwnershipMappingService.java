package com.reshmah.incident.service;

import com.reshmah.incident.dto.OwnershipMappingRequest;
import com.reshmah.incident.dto.OwnershipMappingResponse;
import com.reshmah.incident.entity.OwnershipEntity;
import com.reshmah.incident.repository.OwnershipMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnershipMappingService {

    private final OwnershipMappingRepository ownershipMappingRepository;

    /**
     * Creates a new category → team mapping
     */
    public OwnershipMappingResponse createMapping(OwnershipMappingRequest request) {
        OwnershipEntity mapping = new OwnershipEntity();
        mapping.setCategory(request.getCategory());
        mapping.setTeamName(request.getTeamName());
        mapping.setContactEmail(request.getContactEmail());

        OwnershipEntity saved = ownershipMappingRepository.save(mapping);
        return OwnershipMappingResponse.responseFromEntity(saved);
    }

    /**
     * Fetches all category → team mappings
     */
    public List<OwnershipMappingResponse> getAllMappings() {
        return ownershipMappingRepository.findAll()
                .stream()
                .map(OwnershipMappingResponse::responseFromEntity)
                .toList();
    }

    /**
     * Gets a mapping by id
     */
    public OwnershipMappingResponse getMappingById(Long id) {
        OwnershipEntity mapping = ownershipMappingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ownership mapping not found with id: " + id));
        return OwnershipMappingResponse.responseFromEntity(mapping);
    }

    /**
     * Updates an existing mapping
     */
    public OwnershipMappingResponse updateMapping(Long id, OwnershipMappingRequest request) {
        OwnershipEntity mapping = ownershipMappingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ownership mapping not found with id: " + id));

        mapping.setCategory(request.getCategory());
        mapping.setTeamName(request.getTeamName());
        mapping.setContactEmail(request.getContactEmail());

        OwnershipEntity updated = ownershipMappingRepository.save(mapping);
        return OwnershipMappingResponse.responseFromEntity(updated);
    }

    /**
     * Deletes a mapping by id
     */
    public void deleteMapping(Long id) {
        if (!ownershipMappingRepository.existsById(id)) {
            throw new RuntimeException("Ownership mapping not found with id: " + id);
        }
        ownershipMappingRepository.deleteById(id);
    }
}