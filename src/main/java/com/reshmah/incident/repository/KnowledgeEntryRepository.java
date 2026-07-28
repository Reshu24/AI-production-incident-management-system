package com.reshmah.incident.repository;

import com.reshmah.incident.entity.KnowledgeEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KnowledgeEntryRepository extends JpaRepository<KnowledgeEntry, Long> {
    @Override
    long count();
}
