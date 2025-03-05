package com.spring.profile_service.mappers;

import com.spring.profile_service.dtos.requests.UserProfileCreationRequest;
import com.spring.profile_service.dtos.responses.UserProfileResponse;
import com.spring.profile_service.entities.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(UserProfileCreationRequest request);
    UserProfileResponse toUserProfileResponse(UserProfile userProfile);
}
