package com.spring.api_gateway.services;

import com.spring.api_gateway.dtos.requests.IntrospectRequest;
import com.spring.api_gateway.dtos.responses.ApiResponse;
import com.spring.api_gateway.dtos.responses.IntrospectResponse;
import com.spring.api_gateway.repositories.IdentityClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdentityService {
    IdentityClient identityClient;

    public Mono<ApiResponse<IntrospectResponse, Void>> introspect(String token) {
        return identityClient.introspect(IntrospectRequest.builder()
                .token(token)
                .build());
    }
}
