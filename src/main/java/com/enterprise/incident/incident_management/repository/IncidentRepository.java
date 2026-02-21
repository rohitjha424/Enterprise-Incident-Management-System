package com.enterprise.incident.incident_management.repository;

import com.enterprise.incident.incident_management.entity.Incident;
import com.enterprise.incident.incident_management.entity.IncidentPriority;
import com.enterprise.incident.incident_management.entity.IncidentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IncidentRepository extends JpaRepository<Incident, Long>, JpaSpecificationExecutor<Incident> {

    List<Incident> findByStatus(IncidentStatus status);

    Page<Incident> findByStatusAndPriority(
            IncidentStatus status,
            IncidentPriority priority,
            Pageable pageable);
}
