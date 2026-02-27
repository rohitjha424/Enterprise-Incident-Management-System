package com.enterprise.incident.incident_management.event;



import java.time.LocalDateTime;

public class IncidentCreatedEvent {

    private Long id;
    private String title;
    private String priority;
    private String reportedBy;
    private String createdAt;
    
    
    
	public IncidentCreatedEvent(Long id, String title, String priority, String reportedBy, String createdAt) {
		super();
		this.id = id;
		this.title = title;
		this.priority = priority;
		this.reportedBy = reportedBy;
		this.createdAt = createdAt;
	}


	public IncidentCreatedEvent() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getReportedBy() {
		return reportedBy;
	}
	public void setReportedBy(String reportedBy) {
		this.reportedBy = reportedBy;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}


	@Override
	public String toString() {
		return "IncidentCreatedEvent [id=" + id + ", title=" + title + ", priority=" + priority + ", reportedBy="
				+ reportedBy + ", createdAt=" + createdAt + "]";
	}
	
	
	
}