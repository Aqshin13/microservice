package com.microservice.config;

import com.microservice.auth_service.dto.EmailDTO;
import com.microservice.email_service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

   @Autowired
   private EmailService emailService;


    @KafkaListener(topics = "email-topic", groupId = "email-group")
    public void sendEmail(EmailDTO user) {
        System.out.println("Email is sending...");
        emailService.sendActivationToken(user.getEmail(), user.getActivationToken());
        System.out.println("Email sent");
    }
}
