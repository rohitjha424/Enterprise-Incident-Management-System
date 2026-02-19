package com.enterprise.incident.incident_management.service;


import com.enterprise.incident.incident_management.entity.Incident;
import com.enterprise.incident.incident_management.entity.IncidentStatus;
import com.enterprise.incident.incident_management.exception.ResourceNotFoundException;
import com.enterprise.incident.incident_management.repository.IncidentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;

    public IncidentService(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    public Incident createIncident(Incident incident) {
        return incidentRepository.save(incident);
    }

    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }

    public Incident getIncidentById(Long id) {
        return incidentRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException("Incident not found with id: " + id)
                );
    }


    public List<Incident> getIncidentsByStatus(IncidentStatus status) {
        return incidentRepository.findByStatus(status);
    }

    public void deleteIncident(Long id) {
        incidentRepository.deleteById(id);
    }
    
    public Incident updateIncident(Long id, Incident updatedIncident) {

        return incidentRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(updatedIncident.getTitle());
                    existing.setDescription(updatedIncident.getDescription());
                    existing.setStatus(updatedIncident.getStatus());
                    existing.setPriority(updatedIncident.getPriority());
                    return incidentRepository.save(existing);
                })
                .orElseThrow(() -> 
                    new ResourceNotFoundException("Incident not found with id: " + id)
                );
    }


}
