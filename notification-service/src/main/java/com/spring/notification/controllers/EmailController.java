package com.spring.notification.controllers;

import com.spring.notification.dtos.requests.SendEmailRequest;
import com.spring.notification.dtos.responses.ApiResponse;
import com.spring.notification.dtos.responses.EmailResponse;
import com.spring.notification.services.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailController {
    EmailService emailService;

    @PostMapping("/email/send")
    ApiResponse<EmailResponse, Void> sendEmail(@RequestBody SendEmailRequest request){
        return ApiResponse.<EmailResponse, Void>builder()
                .result(emailService.sendEmail(request))
                .build();
    }

}
