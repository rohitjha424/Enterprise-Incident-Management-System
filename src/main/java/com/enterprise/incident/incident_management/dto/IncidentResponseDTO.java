package com.enterprise.incident.incident_management.dto;



import com.enterprise.incident.incident_management.entity.IncidentPriority;
import com.enterprise.incident.incident_management.entity.IncidentStatus;

import java.time.LocalDateTime;

public class IncidentResponseDTO {

    private Long id;
    private String title;
    private String description;
    private IncidentStatus status;
    private IncidentPriority priority;
    private LocalDateTime createdAt;

    // Getters & Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public IncidentStatus getStatus() { return status; }
    public void setStatus(IncidentStatus status) { this.status = status; }

    public IncidentPriority getPriority() { return priority; }
    public void setPriority(IncidentPriority priority) { this.priority = priority; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}


//We removed comments
//We removed reportedBy
//We control what frontend sees.