package com.reshmah.incident.controller;

import com.reshmah.incident.dto.IncidentRequest;
import com.reshmah.incident.dto.IncidentResponse;
import com.reshmah.incident.dto.MatchResult;
import com.reshmah.incident.dto.TriageResult;
import com.reshmah.incident.service.IncidentService;
import com.reshmah.incident.service.TriageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/incidents")
public class IncidentController {
    private final IncidentService incidentService;
    private final TriageService triageService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<IncidentResponse> createIncident(@Valid @RequestBody IncidentRequest request){
        IncidentResponse response = incidentService.createIncident(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT', 'VIEWER')")
    @GetMapping
    public ResponseEntity<List<IncidentResponse>> getAllIncidents(){
        List<IncidentResponse> response = incidentService.getAllIncidents();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT', 'VIEWER')")
    @GetMapping("/{id}")
    public ResponseEntity<IncidentResponse> getIncidentById(@PathVariable Long id){
        return ResponseEntity.ok(incidentService.getIncidentById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT', 'VIEWER')")
    @GetMapping("/match")
    public ResponseEntity<List<MatchResult>> findMatches(@RequestParam String description) {
        return ResponseEntity.ok(incidentService.findSimilarIncidents(description));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT')")
    @PostMapping("/triage")
    public ResponseEntity<TriageResult> triageIncident(@Valid @RequestBody IncidentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(triageService.triageIncident(request));
    }
}
