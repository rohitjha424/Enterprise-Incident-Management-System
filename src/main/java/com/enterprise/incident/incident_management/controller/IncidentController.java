package com.enterprise.incident.incident_management.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;

import com.enterprise.incident.incident_management.dto.IncidentRequestDTO;
import com.enterprise.incident.incident_management.dto.IncidentResponseDTO;
import com.enterprise.incident.incident_management.entity.IncidentPriority;
import com.enterprise.incident.incident_management.entity.IncidentStatus;
import com.enterprise.incident.incident_management.service.IncidentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<IncidentResponseDTO> createIncident(
            @Valid @RequestBody IncidentRequestDTO dto) {

        return new ResponseEntity<>(
                incidentService.createIncident(dto),
                HttpStatus.CREATED);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<Page<IncidentResponseDTO>> getAllIncidents(
            @RequestParam(required = false) IncidentStatus status,
            @RequestParam(required = false) IncidentPriority priority,
            @PageableDefault(size = 5, sort = "createdAt") Pageable pageable) {

        return ResponseEntity.ok(
                incidentService.getAllIncidents(status, priority, pageable));
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<IncidentResponseDTO> getIncidentById(@PathVariable Long id) {
        return ResponseEntity.ok(incidentService.getIncidentById(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<IncidentResponseDTO> updateIncident(
            @PathVariable Long id,
            @Valid @RequestBody IncidentRequestDTO dto) {

        return ResponseEntity.ok(
                incidentService.updateIncident(id, dto));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncident(@PathVariable Long id) {
        incidentService.deleteIncident(id);
        return ResponseEntity.noContent().build();
    }
}
