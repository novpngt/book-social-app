package com.spring.identity.repositories.httpClients;

import com.spring.identity.configurations.AuthenticationRequestInterceptor;
import com.spring.identity.dtos.requests.UserProfileCreationRequest;
import com.spring.identity.dtos.responses.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//Auto add authorization header to request via configuration (just this class applied)
@FeignClient(
        name = "user-profile-service",
        url = "${app.services.user-profile-client-url}",
        configuration = {AuthenticationRequestInterceptor.class}
)
public interface UserProfileClient {
    @PostMapping(value = "/internal/users/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    UserProfileResponse createUserProfile(@RequestBody UserProfileCreationRequest userProfile);
}
