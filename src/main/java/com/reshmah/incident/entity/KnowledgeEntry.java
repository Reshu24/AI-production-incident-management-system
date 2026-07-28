package com.reshmah.incident.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "knowledge_entries")
@Data
public class KnowledgeEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2000)
    private String description;

    private String affectedService;

    @Enumerated(EnumType.STRING)
    private IncidentEntity.Severity severity;

    @Column(length = 2000)
    private String rootCause;

    @Column(length = 2000)
    private String resolutionNotes;

    private LocalDateTime resolvedAt;

    @PrePersist
    protected void onCreate() {
        resolvedAt = LocalDateTime.now();
    }
}