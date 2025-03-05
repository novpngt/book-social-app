package com.spring.identity_service.mappers;

import com.spring.identity_service.dtos.requests.UserCreateRequest;
import com.spring.identity_service.dtos.requests.UserProfileCreationRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    UserProfileCreationRequest toUserProfileCreationRequest(UserCreateRequest request);
}
