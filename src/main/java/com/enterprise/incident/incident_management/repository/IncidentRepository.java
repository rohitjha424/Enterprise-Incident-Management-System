package com.enterprise.incident.incident_management.repository;

import com.enterprise.incident.incident_management.entity.Incident;
import com.enterprise.incident.incident_management.entity.IncidentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidentRepository extends JpaRepository<Incident, Long> {

    List<Incident> findByStatus(IncidentStatus status);
}