package com.spring.notification.controllers;

import com.spring.event.dtos.NotificationEvent;
import com.spring.notification.dtos.requests.Recipient;
import com.spring.notification.dtos.requests.SendEmailRequest;
import com.spring.notification.services.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NotificationController {

    EmailService emailService;

    @KafkaListener(topics = "notification-delivery")
    public void listenNotificationEvent(NotificationEvent msg){
        emailService.sendEmail(SendEmailRequest.builder()
                        .to(Recipient.builder()
                                .email(msg.getRecipient())
                                .build())
                        .subject(msg.getSubject())
                        .htmlContent(msg.getBody())
                .build());
        log.info("Received msg: {}", msg);
    }

}
