package com.reshmah.incident.controller;

import com.reshmah.incident.dto.OwnershipMappingRequest;
import com.reshmah.incident.dto.OwnershipMappingResponse;
import com.reshmah.incident.service.OwnershipMappingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ownership-map")
@RequiredArgsConstructor
public class OwnershipMappingController {

    private final OwnershipMappingService ownershipMappingService;

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT', 'VIEWER')")
    @PostMapping
    public ResponseEntity<OwnershipMappingResponse> createMapping(
            @Valid @RequestBody OwnershipMappingRequest request) {
        OwnershipMappingResponse response = ownershipMappingService.createMapping(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT', 'VIEWER')")
    @GetMapping
    public ResponseEntity<List<OwnershipMappingResponse>> getAllMappings() {
        return ResponseEntity.ok(ownershipMappingService.getAllMappings());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT', 'VIEWER')")
    @GetMapping("/{id}")
    public ResponseEntity<OwnershipMappingResponse> getMappingById(@PathVariable Long id) {
        return ResponseEntity.ok(ownershipMappingService.getMappingById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT', 'VIEWER')")
    @PutMapping("/{id}")
    public ResponseEntity<OwnershipMappingResponse> updateMapping(
            @PathVariable Long id,
            @Valid @RequestBody OwnershipMappingRequest request) {
        return ResponseEntity.ok(ownershipMappingService.updateMapping(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMapping(@PathVariable Long id) {
        ownershipMappingService.deleteMapping(id);
        return ResponseEntity.noContent().build();
    }
}