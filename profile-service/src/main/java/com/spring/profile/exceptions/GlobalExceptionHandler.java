package com.spring.profile.exceptions;

import com.nimbusds.jose.JOSEException;
import com.spring.profile.dtos.responses.ApiResponse;
import com.spring.profile.enums.ErrorCode;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private static final String MIN_ATTRIBUTE = "min";
    private static final String MAX_ATTRIBUTE = "max";

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse<Object, Object>> runTimeExceptionHandler(RuntimeException e) {
        ApiResponse<Object, Object> apiResponse = new ApiResponse<>();
        apiResponse.setCode(ErrorCode.UNCATEGORIZED_ERROR.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_ERROR.getMessage() + ": " + e.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<Object, Object>> appExceptionHandler(AppException e) {
        ErrorCode errorCode = e.getErrorCode();
        ApiResponse<Object, Object> apiResponse = new ApiResponse<>();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse<Object, Object>> accessDeniedExceptionHandler() {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED_ERROR;
        return ResponseEntity.status(errorCode.getHttpStatusCode())
                .body(ApiResponse.builder()
                        .message(errorCode.getMessage())
                        .code(errorCode.getCode())
                        .build());
    }

    @ExceptionHandler(value = ParseException.class)
    ResponseEntity<ApiResponse<Object, Object>> handleParseException(ParseException e) {
        ApiResponse<Object, Object> apiResponse = new ApiResponse<>();
        apiResponse.setCode(ErrorCode.INVALID_JWT_TOKEN.getCode());
        apiResponse.setMessage(ErrorCode.INVALID_JWT_TOKEN.getMessage() + ": " + e.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = JOSEException.class)
    ResponseEntity<ApiResponse<Object, Object>> handleJOSEException(JOSEException e) {
        ApiResponse<Object, Object> apiResponse = new ApiResponse<>();
        apiResponse.setCode(ErrorCode.JWT_VERIFICATION_FAILED.getCode());
        apiResponse.setMessage(ErrorCode.JWT_VERIFICATION_FAILED.getMessage() + ": " + e.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<ApiResponse<Object, Object>> handleMissingRequestBody(HttpMessageNotReadableException e) {
        ApiResponse<Object, Object> apiResponse = new ApiResponse<>();
        apiResponse.setCode(ErrorCode.UNREADABLE_MESSAGE.getCode());
        apiResponse.setMessage(ErrorCode.UNREADABLE_MESSAGE.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<Object, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        List<ValidationError> validationErrors =
                fieldErrors.stream().map(this::convertToValidationError).toList();

        ApiResponse<Object, Object> response = new ApiResponse<>();
        ErrorCode errorCode = ErrorCode.USER_VALIDATION_ERROR;
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());
        response.setErrors(validationErrors);

        return ResponseEntity.badRequest().body(response);
    }

    private ValidationError convertToValidationError(FieldError fieldError) {
        String enumKey = fieldError.getDefaultMessage();
        ErrorCode errorCode = ErrorCode.UNCATEGORIZED_ERROR;
        Map<String, Object> attributes = null;

        try {
            errorCode = ErrorCode.valueOf(enumKey);
            ConstraintViolation<?> constraintViolation = fieldError.unwrap(ConstraintViolation.class);
            attributes = constraintViolation.getConstraintDescriptor().getAttributes();
            log.info("Validation attributes: {}", attributes);
        } catch (IllegalArgumentException ignored) {
        }

        String message;

        if (attributes != null) {
            message = mapAttribute(errorCode.getMessage(), attributes);
        } else {
            message = ErrorCode.UNCATEGORIZED_ERROR.getMessage();
        }

        return new ValidationError(fieldError.getField(), message);
    }

    private String mapAttribute(String message, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));
        String maxValue = String.valueOf(attributes.get(MAX_ATTRIBUTE));
        return message.replace("{min}", minValue).replace("{max}", maxValue);
    }
}
