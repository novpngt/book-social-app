package com.spring.notification.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_ERROR(1002, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHENTICATED_ERROR(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    INVALID_JWT_TOKEN(1007, "Invalid JWT token", HttpStatus.UNAUTHORIZED),
    JWT_VERIFICATION_FAILED(1008, "JWT verification failed", HttpStatus.UNAUTHORIZED),
    UNREADABLE_MESSAGE(1009, "Http message is not readable", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED_ERROR(1010, "You do not have this permission", HttpStatus.FORBIDDEN),
    BAD_CREDENTIALS(1019, "Bad credentials", HttpStatus.UNAUTHORIZED),
    CANNOT_SEND_EMAIL(1020, "Cannot send email", HttpStatus.BAD_REQUEST),
    ;

    int code;
    String message;
    HttpStatusCode httpStatusCode;
}
