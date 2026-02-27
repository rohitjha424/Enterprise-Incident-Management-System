package com.enterprise.incident.incident_management.kafka;


import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.enterprise.incident.incident_management.event.IncidentCreatedEvent;

@Service
public class IncidentEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public IncidentEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendIncidentCreatedEvent(IncidentCreatedEvent event) {
        kafkaTemplate.send("incident-created", event);
    }
}