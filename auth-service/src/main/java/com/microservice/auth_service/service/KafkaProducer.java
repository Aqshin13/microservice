package com.microservice.auth_service.service;

import com.microservice.auth_service.dto.EmailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, EmailDTO> kafkaTemplate;

    public void sendMessage(EmailDTO data) {
        Message<EmailDTO> message = MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, "email-topic")
                .build();
        kafkaTemplate.send(message);

    }

}
