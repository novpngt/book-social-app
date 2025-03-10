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
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
    EmailClient emailClient;

    String apiKey = "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";

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
