package com.spring.profile.controllers;

import com.spring.profile.dtos.responses.ApiResponse;
import com.spring.profile.dtos.responses.UserProfileResponse;
import com.spring.profile.services.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/users")
@Slf4j
public class UserProfileController {
    UserProfileService userProfileService;

    @GetMapping("/{id}")
    public UserProfileResponse getUserProfile(@PathVariable String id){
        return userProfileService.getUserProfileById(id);
    }

    @GetMapping("")
    public ApiResponse<List<UserProfileResponse>,Void> getUserProfiles(){
        return ApiResponse.<List<UserProfileResponse>,Void> builder()
                .result(userProfileService.getAllUserProfiles())
                .build();
    }
}
