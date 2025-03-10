package com.spring.notification.services;

import com.spring.notification.dtos.requests.EmailRequest;
import com.spring.notification.dtos.requests.SendEmailRequest;
import com.spring.notification.dtos.requests.Sender;
import com.spring.notification.dtos.responses.EmailResponse;
import com.spring.notification.enums.ErrorCode;
import com.spring.notification.exceptions.AppException;
import com.spring.notification.repositories.httpClients.EmailClient;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
    EmailClient emailClient;

    @NonFinal
    @Value("${brevo.api-key}")
    String apiKey ;

    public EmailResponse sendEmail(SendEmailRequest request){
        EmailRequest emailRequest = EmailRequest.builder()
                .sender(Sender.builder()
                        .name("spring")
                        .email("ngt.phongg.vn@gmail.com")
                        .build())
                .to(List.of(request.getTo()))
                .subject(request.getSubject())
                .htmlContent(request.getHtmlContent())
                .build();
        try {
            return emailClient.sendEmail(apiKey, emailRequest);
        } catch (FeignException.FeignClientException e) {
            throw new AppException(ErrorCode.CANNOT_SEND_EMAIL);
        }
    }
}
