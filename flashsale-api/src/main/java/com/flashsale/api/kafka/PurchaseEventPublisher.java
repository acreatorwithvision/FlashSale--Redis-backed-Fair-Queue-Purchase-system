package com.flashsale.api.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

// Publishes purchase success events to Kafka

@Component
public class PurchaseEventPublisher {

    private static final String TOPIC= "purchase-events";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public PurchaseEventPublisher(KafkaTemplate<String, String> kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }

    public void publish(String userId, String itemId){
        String event= """
                {
                "userId": "%s",
                "itemId": "%s",
                "timestamp": "%d"
                }
                """.formatted(userId, itemId, Instant.now().toEpochMilli());

        //Fire-and-forget: API must stay fast
        kafkaTemplate.send(TOPIC, itemId, event);
    }
}
