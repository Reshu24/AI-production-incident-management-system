package com.reshmah.incident.repository;

import com.reshmah.incident.entity.OwnershipEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnershipMappingRepository extends JpaRepository<OwnershipEntity, Long> {
    Optional<OwnershipEntity> findByCategory(String category);

    @Override
    long count();
}