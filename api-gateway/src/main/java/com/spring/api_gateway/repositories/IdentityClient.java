package com.spring.api_gateway.repositories;

import com.spring.api_gateway.dtos.requests.IntrospectRequest;
import com.spring.api_gateway.dtos.responses.ApiResponse;
import com.spring.api_gateway.dtos.responses.IntrospectResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

public interface IdentityClient {
    @PostExchange(url = "/auth/introspect", contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<ApiResponse<IntrospectResponse, Void>> introspect(@RequestBody IntrospectRequest request);
}
