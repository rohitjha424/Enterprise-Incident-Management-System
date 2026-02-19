package com.enterprise.incident.incident_management.dto;


import com.enterprise.incident.incident_management.entity.IncidentPriority;
import com.enterprise.incident.incident_management.entity.IncidentStatus;

public class IncidentRequestDTO {

    private String title;
    private String description;
    private IncidentStatus status;
    private IncidentPriority priority;

    // Getters & Setters

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public IncidentStatus getStatus() { return status; }
    public void setStatus(IncidentStatus status) { this.status = status; }

    public IncidentPriority getPriority() { return priority; }
    public void setPriority(IncidentPriority priority) { this.priority = priority; }
}

//No id
//No createdAt
//No comments
//No reportedBy
//Client should not control those.
