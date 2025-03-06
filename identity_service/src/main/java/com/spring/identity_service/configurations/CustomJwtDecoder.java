package com.spring.identity_service.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import com.spring.identity_service.exceptions.AppException;
import com.spring.identity_service.services.AuthenticationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class CustomJwtDecoder implements JwtDecoder {

    AuthenticationService authenticationService;

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            return authenticationService.verifyToken(token);
        } catch (AppException e) {
            log.error(e.getMessage());
            throw new BadCredentialsException("Invalid token", e);
        }
    }
}
