package com.spring.profile.controllers;

import com.spring.profile.dtos.requests.UserProfileCreationRequest;
import com.spring.profile.dtos.responses.UserProfileResponse;
import com.spring.profile.services.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/internal/users")
@Slf4j
public class InternalUserProfileController {
    UserProfileService userProfileService;

    @PostMapping("/registration")
    public UserProfileResponse createUserProfile(@RequestBody UserProfileCreationRequest request){
        UserProfileResponse userProfileResponse = userProfileService.createUserProfile(request);
        log.info(userProfileResponse.toString());
        return userProfileResponse;
    }
}
