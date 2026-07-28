package com.reshmah.incident.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "assignment_decisions")
public class AssignmentDecision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // which incident triggered this decision
    @Column(nullable = false)
    private Long incidentId;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String assignedTeam;

    @Column(nullable = false)
    private String contactEmail;

    @Column(nullable = false)
    private double confidenceScore;

    @Column(nullable = false)
    private boolean isAutoAssigned;     // true = auto assigned, false = suggested only

    @Column(length = 1000)
    private String matchedKeywords;

    // id of the past KnowledgeEntry that matched (if any)
    private Long matchedKnowledgeEntryId;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}