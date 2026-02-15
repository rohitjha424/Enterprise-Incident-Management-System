package com.enterprise.incident.incident_management.repository;

import com.enterprise.incident.incident_management.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByIncidentId(Long incidentId);
}