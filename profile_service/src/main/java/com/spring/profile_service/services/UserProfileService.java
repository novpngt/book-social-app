package com.spring.profile_service.services;

import com.spring.profile_service.dtos.requests.UserProfileCreationRequest;
import com.spring.profile_service.dtos.responses.UserProfileResponse;
import com.spring.profile_service.entities.UserProfile;
import com.spring.profile_service.mappers.UserProfileMapper;
import com.spring.profile_service.repository.UserProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@Slf4j
public class UserProfileService {
    UserProfileRepository userProfileRepository;
    UserProfileMapper userProfileMapper;

    public UserProfileResponse createUserProfile(UserProfileCreationRequest request){
        UserProfile userProfile = userProfileMapper.toUserProfile(request);
        userProfile = userProfileRepository.save(userProfile);
        return userProfileMapper.toUserProfileResponse(userProfile);
    }

    public UserProfileResponse getUserProfileById(String id){
        UserProfile userProfile = userProfileRepository.findById(id).orElseThrow(()->new RuntimeException("User profile not found"));
        return userProfileMapper.toUserProfileResponse(userProfile);
    }
}
