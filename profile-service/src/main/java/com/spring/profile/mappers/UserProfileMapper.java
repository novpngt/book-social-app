package com.spring.profile.mappers;

import com.spring.profile.dtos.requests.UserProfileCreationRequest;
import com.spring.profile.dtos.responses.UserProfileResponse;
import com.spring.profile.entities.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(UserProfileCreationRequest request);
    UserProfileResponse toUserProfileResponse(UserProfile userProfile);
}
