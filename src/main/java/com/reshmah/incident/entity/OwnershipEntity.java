package com.reshmah.incident.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ownership_mappings")
public class OwnershipEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String category;

    @Column(nullable = false)
    private String teamName;

    @Column(nullable = false)
    private String contactEmail;
}