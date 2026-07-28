package com.reshmah.incident.service;

import com.reshmah.incident.dto.IncidentRequest;
import com.reshmah.incident.dto.IncidentResponse;
import com.reshmah.incident.dto.MatchResult;
import com.reshmah.incident.dto.TriageResult;
import com.reshmah.incident.entity.AssignmentDecision;
import com.reshmah.incident.entity.IncidentEntity;
import com.reshmah.incident.entity.OwnershipEntity;
import com.reshmah.incident.matching.KeywordClassifier;
import com.reshmah.incident.repository.AssignmentDecisionRepository;
import com.reshmah.incident.repository.IncidentRepository;
import com.reshmah.incident.repository.OwnershipMappingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TriageService {

    private final IncidentRepository incidentRepository;
    private final IncidentService incidentService;
    private final OwnershipMappingRepository ownershipMappingRepository;
    private final AssignmentDecisionRepository assignmentDecisionRepository;
    private final KeywordClassifier keywordClassifier;

    /**
     * Full triage pipeline:
     * 1. Save incident
     * 2. Classify text → category + confidence
     * 3. Lookup ownership map → team
     * 4. Decide auto-assign vs suggest
     * 5. Save AssignmentDecision (audit trail)
     * 6. Find similar past incidents
     * 7. Return full TriageResult
     */
    public TriageResult triageIncident(IncidentRequest request){

        //saves incident in INCIDENTS table
        IncidentEntity incident = new IncidentEntity();
        incident.setTitle(request.getTitle());
        incident.setDescription(request.getDescription());
        incident.setSeverity(request.getSeverity());
        incident.setAffectedService(request.getAffectedService());

        IncidentEntity saved = incidentRepository.save(incident);
        IncidentResponse incidentResponse = IncidentResponse.responseFromEntity(saved);


        //text classifier
        KeywordClassifier.ClassificationResult classification = keywordClassifier.classify(request.getDescription());

        Optional<OwnershipEntity> ownershipMapping = ownershipMappingRepository.findByCategory(classification.getCategory());

        String assignedTeam = ownershipMapping.map(OwnershipEntity::getTeamName)
                .orElse("Unassigned");
        String contactEmail = ownershipMapping.map(OwnershipEntity::getContactEmail)
                .orElse("support@company.com");

        // Step 4 — auto assign or suggest based on confidence threshold
        boolean isAutoAssigned = classification.isAutoAssign() && ownershipMapping.isPresent();
        String decisionLabel = isAutoAssigned ? "AUTO ASSIGNED" : "SUGGESTED";

        log.info("Decision: {} → team: {}", decisionLabel, assignedTeam);

        // Step 5 — save AssignmentDecision for audit trail
        AssignmentDecision decision = new AssignmentDecision();
        decision.setIncidentId(saved.getId());
        decision.setCategory(classification.getCategory());
        decision.setAssignedTeam(assignedTeam);
        decision.setContactEmail(contactEmail);
        decision.setConfidenceScore(classification.getConfidenceScore());
        decision.setAutoAssigned(isAutoAssigned);
        decision.setMatchedKeywords(classification.getMatchedKeywords());
        assignmentDecisionRepository.save(decision);

        //find similar incidents
        List<MatchResult> similarIncidents = incidentService.findSimilarIncidents(saved.getDescription());
        // if a strong knowledge entry match exists, record it in the decision
        if (!similarIncidents.isEmpty() && similarIncidents.getFirst().getSimilarityScore() > 0.5) {
            decision.setMatchedKnowledgeEntryId(similarIncidents.getFirst().getKnowledgeEntryId());
            assignmentDecisionRepository.save(decision);
        }

        //build and return the full triage result
        TriageResult.ClassificationDecision classificationDecision =
                new TriageResult.ClassificationDecision(
                        classification.getCategory(),
                        assignedTeam,
                        contactEmail,
                        classification.getConfidenceScore(),
                        isAutoAssigned,
                        classification.getMatchedKeywords(),
                        decisionLabel
                );

        return new TriageResult(incidentResponse, similarIncidents, classificationDecision);
    }

}
