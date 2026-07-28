package com.reshmah.incident.repository;

import com.reshmah.incident.entity.AssignmentDecision;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssignmentDecisionRepository extends JpaRepository<AssignmentDecision, Long> {

    // get the decision made for a specific incident
    Optional<AssignmentDecision> findByIncidentId(Long incidentId);

    // get all decisions made for a specific team
    List<AssignmentDecision> findByAssignedTeam(String assignedTeam);
}