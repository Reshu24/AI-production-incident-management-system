package com.reshmah.incident.controller;

import com.reshmah.incident.dto.KnowledgeEntryRequest;
import com.reshmah.incident.dto.KnowledgeEntryResponse;
import com.reshmah.incident.entity.KnowledgeEntry;
import com.reshmah.incident.service.KnowledgeEntryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/knowledge")
@RequiredArgsConstructor
public class KnowledgeEntryController {

    private final KnowledgeEntryService knowledgeEntryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<KnowledgeEntryResponse> createEntry(@Valid @RequestBody KnowledgeEntryRequest request){
        KnowledgeEntryResponse res = knowledgeEntryService.createEntry(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT', 'VIEWER')")
    @GetMapping
    public ResponseEntity<List<KnowledgeEntryResponse>> getAllEntries(){
        return ResponseEntity.ok(knowledgeEntryService.getAllEntries());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT', 'VIEWER')")
    @GetMapping("/{id}")
    public ResponseEntity<KnowledgeEntryResponse> getEntryById(@PathVariable Long id) {
        return ResponseEntity.ok(knowledgeEntryService.getEntryById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT')")
    @PutMapping("/{id}")
    public ResponseEntity<KnowledgeEntryResponse> updateEntry(
            @PathVariable Long id,
            @Valid @RequestBody KnowledgeEntryRequest request) {
        return ResponseEntity.ok(knowledgeEntryService.updateEntryInDb(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable Long id) {
        knowledgeEntryService.deleteEntry(id);
        return ResponseEntity.noContent().build();
    }
}
