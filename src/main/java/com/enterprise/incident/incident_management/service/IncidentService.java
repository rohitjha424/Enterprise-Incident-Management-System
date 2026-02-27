package com.enterprise.incident.incident_management.service;

import com.enterprise.incident.incident_management.dto.IncidentRequestDTO;
import com.enterprise.incident.incident_management.dto.IncidentResponseDTO;
import com.enterprise.incident.incident_management.entity.Incident;
import com.enterprise.incident.incident_management.entity.IncidentPriority;
import com.enterprise.incident.incident_management.entity.IncidentStatus;
import com.enterprise.incident.incident_management.entity.User;
import com.enterprise.incident.incident_management.exception.ResourceNotFoundException;
import com.enterprise.incident.incident_management.kafka.IncidentEventProducer;
import com.enterprise.incident.incident_management.repository.IncidentRepository;
import com.enterprise.incident.incident_management.repository.UserRepository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.enterprise.incident.incident_management.event.IncidentCreatedEvent;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final UserRepository userRepository;
    private final IncidentEventProducer incidentEventProducer;

    public IncidentService(IncidentRepository incidentRepository,
            UserRepository userRepository, IncidentEventProducer incidentEventProducer ) {
        this.incidentRepository = incidentRepository;
        this.userRepository = userRepository;
        this.incidentEventProducer = incidentEventProducer;
    }

    // CREATE
    public IncidentResponseDTO createIncident(IncidentRequestDTO dto) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Incident incident = new Incident();
        incident.setTitle(dto.getTitle());
        incident.setDescription(dto.getDescription());
        incident.setStatus(dto.getStatus());
        incident.setPriority(dto.getPriority());
        incident.setReportedBy(currentUser);   // 🔥 IMPORTANT

        Incident saved = incidentRepository.save(incident);
        
        IncidentCreatedEvent event = new IncidentCreatedEvent();
        event.setId(saved.getId());
        event.setTitle(saved.getTitle());
        event.setPriority(saved.getPriority().name());
        event.setReportedBy(currentUser.getEmail());
        event.setCreatedAt(saved.getCreatedAt().toString());

        incidentEventProducer.sendIncidentCreatedEvent(event);

        return mapToResponseDTO(saved);
    }

    // GET ALL
    public Page<IncidentResponseDTO> getAllIncidents(
            IncidentStatus status,
            IncidentPriority priority,
            Pageable pageable) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Specification<Incident> spec = (root, query, cb) -> {

            Predicate predicate = cb.conjunction();

            //ROLE LOGIC
            if (!currentUser.getRole().equals("ADMIN")) {
                predicate = cb.and(predicate,
                        cb.equal(root.get("reportedBy"), currentUser));
            }

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
                .orElseThrow(() -> new ResourceNotFoundException("Incident not found with id: " + id));

        return mapToResponseDTO(incident);
    }

    // UPDATE
    public IncidentResponseDTO updateIncident(Long id, IncidentRequestDTO dto) {

        Incident existing = incidentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incident not found with id: " + id));

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
