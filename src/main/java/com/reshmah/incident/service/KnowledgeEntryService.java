package com.reshmah.incident.service;

import com.reshmah.incident.dto.IncidentRequest;
import com.reshmah.incident.dto.IncidentResponse;
import com.reshmah.incident.dto.KnowledgeEntryRequest;
import com.reshmah.incident.dto.KnowledgeEntryResponse;
import com.reshmah.incident.entity.IncidentEntity;
import com.reshmah.incident.entity.KnowledgeEntry;
import com.reshmah.incident.repository.KnowledgeEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KnowledgeEntryService {

    private final KnowledgeEntryRepository knowledgeEntryRepository;

    /**
     * Creates a new knowledge entry
     */
    public KnowledgeEntryResponse createEntry(KnowledgeEntryRequest request) {
        KnowledgeEntry entry = new KnowledgeEntry();
        entry.setTitle(request.getTitle());
        entry.setDescription(request.getDescription());
        entry.setAffectedService(request.getAffectedService());
        entry.setSeverity(request.getSeverity());
        entry.setRootCause(request.getRootCause());
        entry.setResolutionNotes(request.getResolutionNotes());

        KnowledgeEntry saved = knowledgeEntryRepository.save(entry);
        return KnowledgeEntryResponse.responseFromEntity(saved);
    }

    public List<KnowledgeEntryResponse> getAllEntries() {
        return knowledgeEntryRepository.findAll()
                .stream()
                .map(KnowledgeEntryResponse::responseFromEntity)
                .toList();
    }

    public KnowledgeEntryResponse updateEntryInDb(Long id, KnowledgeEntryRequest request){
        KnowledgeEntry entry = knowledgeEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Knowledge entry not found with id: " + id));
        entry.setTitle(request.getTitle());
        entry.setDescription(request.getDescription());
        entry.setAffectedService(request.getAffectedService());
        entry.setSeverity(request.getSeverity());
        entry.setRootCause(request.getRootCause());
        entry.setResolutionNotes(request.getResolutionNotes());

        KnowledgeEntry updated = knowledgeEntryRepository.save(entry);
        return KnowledgeEntryResponse.responseFromEntity(updated);
    }

    /**
     * Gets a knowledge entry by id
     */
    public KnowledgeEntryResponse getEntryById(Long id) {
        KnowledgeEntry entry = knowledgeEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Knowledge entry not found with id: " + id));
        return KnowledgeEntryResponse.responseFromEntity(entry);
    }

    /**
     * Deletes a knowledge entry by id
     */
    public void deleteEntry(Long id) {
        if (!knowledgeEntryRepository.existsById(id)) {
            throw new RuntimeException("Knowledge entry not found with id: " + id);
        }
        knowledgeEntryRepository.deleteById(id);
    }
}
