package com.enterprise.incident.incident_management.service;


import com.enterprise.incident.incident_management.entity.Comment;
import com.enterprise.incident.incident_management.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByIncident(Long incidentId) {
        return commentRepository.findByIncidentId(incidentId);
    }
}
