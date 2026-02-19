package com.enterprise.incident.incident_management.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;

import com.enterprise.incident.incident_management.entity.Incident;
import com.enterprise.incident.incident_management.service.IncidentService;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    // CREATE
    @PostMapping
    public Incident createIncident(@RequestBody Incident incident) {
        return incidentService.createIncident(incident);
    }

    // GET ALL
    @GetMapping
    public List<Incident> getAllIncidents() {
        return incidentService.getAllIncidents();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public Optional<Incident> getIncidentById(@PathVariable Long id) {
        return incidentService.getIncidentById(id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteIncident(@PathVariable Long id) {
        incidentService.deleteIncident(id);
        return "Incident deleted successfully";
    }
}
