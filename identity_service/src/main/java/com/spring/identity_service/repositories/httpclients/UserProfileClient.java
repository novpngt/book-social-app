package com.spring.identity_service.repositories.httpclients;

import com.spring.identity_service.dtos.requests.UserProfileCreationRequest;
import com.spring.identity_service.dtos.responses.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-profile-service", url = "${app.services.user-profile-client-url}")
public interface UserProfileClient {
    @PostMapping(value = "/internal/users/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    UserProfileResponse createUserProfile(@RequestBody UserProfileCreationRequest userProfile);
}
