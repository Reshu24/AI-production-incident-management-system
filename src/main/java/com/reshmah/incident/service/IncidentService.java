package com.reshmah.incident.service;

import com.reshmah.incident.dto.IncidentRequest;
import com.reshmah.incident.dto.IncidentResponse;
import com.reshmah.incident.dto.MatchResult;
import com.reshmah.incident.entity.IncidentEntity;
import com.reshmah.incident.entity.KnowledgeEntry;
import com.reshmah.incident.matching.TextSimilarityService;
import com.reshmah.incident.repository.IncidentRepository;
import com.reshmah.incident.repository.KnowledgeEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final KnowledgeEntryRepository knowledgeEntryRepository;
    private final TextSimilarityService similarityService;

    /**
     * Used to create an incident
     *
     * @param request
     * @return
     */
    public IncidentResponse createIncident(IncidentRequest request) {
        IncidentEntity incident = new IncidentEntity();
        incident.setTitle(request.getTitle());
        incident.setDescription(request.getDescription());
        incident.setAffectedService(request.getAffectedService());
        incident.setSeverity(request.getSeverity());

        IncidentEntity saveValuesInDb = incidentRepository.save(incident);
        return IncidentResponse.responseFromEntity(saveValuesInDb); //not returning the response directly because, never expose your database entity structure directly to API consumers. It decouples your internal data model from your API contract.
    }

    /**
     * fetches all the incidents
     * @return
     */
    public List<IncidentResponse> getAllIncidents() {
        return incidentRepository.findAll()
                .stream()
                .map(IncidentResponse::responseFromEntity)
                .toList();
    }

    /**
     * gets the incidents based on id
     * @param id
     * @return
     */
    public IncidentResponse getIncidentById(Long id){
        IncidentEntity entity = incidentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Incident not found with id: " + id));
        return IncidentResponse.responseFromEntity(entity);
    }

    /**
     * Used to find similar incidents
     * @param description
     * @return
     */
    public List<MatchResult> findSimilarIncidents(String description) {
        List<KnowledgeEntry> allEntries = knowledgeEntryRepository.findAll();

        return allEntries.stream()
                .map(entry -> new MatchResult(
                        entry.getId(),
                        entry.getTitle(),
                        similarityService.calculateSimilarity(description, entry.getDescription()),
                        entry.getResolutionNotes(),
                        entry.getRootCause()
                ))
                .filter(match -> match.getSimilarityScore() > 0.1) // ignore near-zero noise matches
                .sorted(Comparator.comparingDouble(MatchResult::getSimilarityScore).reversed())
                .limit(5) // top 5 matches only
                .toList();
    }
}
