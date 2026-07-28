package com.reshmah.incident.repository;

import com.reshmah.incident.entity.IncidentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidentRepository extends JpaRepository<IncidentEntity, Long> {
    List<IncidentEntity> id(Long id);
    long countByStatus(IncidentEntity.Status status);
    long countBySeverity(IncidentEntity.Severity severity);
}
