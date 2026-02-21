package com.enterprise.incident.incident_management.service;


import com.enterprise.incident.incident_management.dto.IncidentRequestDTO;
import com.enterprise.incident.incident_management.dto.IncidentResponseDTO;
import com.enterprise.incident.incident_management.entity.Incident;
import com.enterprise.incident.incident_management.entity.IncidentPriority;
import com.enterprise.incident.incident_management.entity.IncidentStatus;
import com.enterprise.incident.incident_management.exception.ResourceNotFoundException;
import com.enterprise.incident.incident_management.repository.IncidentRepository;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;

    public IncidentService(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    // CREATE
    public IncidentResponseDTO createIncident(IncidentRequestDTO dto) {

        Incident incident = new Incident();
        incident.setTitle(dto.getTitle());
        incident.setDescription(dto.getDescription());
        incident.setStatus(dto.getStatus());
        incident.setPriority(dto.getPriority());

        Incident saved = incidentRepository.save(incident);

        return mapToResponseDTO(saved);
    }

    // GET ALL
    public Page<IncidentResponseDTO> getAllIncidents(
        IncidentStatus status,
        IncidentPriority priority,
        Pageable pageable) {

    Specification<Incident> spec = (root, query, cb) -> {

        Predicate predicate = cb.conjunction();

        if (status != null) {
            predicate = cb.and(predicate,
                    cb.equal(root.get("status"), status));
        }

        if (priority != null) {
            predicate = cb.and(predicate,
                    cb.equal(root.get("priority"), priority));
        }

        return predicate;
    };

    return incidentRepository.findAll(spec, pageable)
            .map(this::mapToResponseDTO);
}

    // GET BY ID
    public IncidentResponseDTO getIncidentById(Long id) {

        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Incident not found with id: " + id)
                );

        return mapToResponseDTO(incident);
    }

    // UPDATE
    public IncidentResponseDTO updateIncident(Long id, IncidentRequestDTO dto) {

        Incident existing = incidentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Incident not found with id: " + id)
                );

        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setStatus(dto.getStatus());
        existing.setPriority(dto.getPriority());

        Incident updated = incidentRepository.save(existing);

        return mapToResponseDTO(updated);
    }

    // DELETE
    public void deleteIncident(Long id) {

        if (!incidentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Incident not found with id: " + id);
        }

        incidentRepository.deleteById(id);
    }

    // MAPPER
    private IncidentResponseDTO mapToResponseDTO(Incident incident) {

        IncidentResponseDTO dto = new IncidentResponseDTO();

        dto.setId(incident.getId());
        dto.setTitle(incident.getTitle());
        dto.setDescription(incident.getDescription());
        dto.setStatus(incident.getStatus());
        dto.setPriority(incident.getPriority());
        dto.setCreatedAt(incident.getCreatedAt());

        return dto;
    }
}

