package com.reshmah.incident.entity;

import jakarta.persistence.*;
import lombok.Data;

import javax.annotation.processing.SupportedAnnotationTypes;

@Table(name = "users")
@Data
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean enabled = true;

    public enum Role{
        ADMIN,
        SUPPORT,
        VIEWER
    }
}
