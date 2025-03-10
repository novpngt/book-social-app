package com.spring.identity.mappers;

import com.spring.identity.dtos.requests.UserCreateRequest;
import com.spring.identity.dtos.requests.UserProfileCreationRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    UserProfileCreationRequest toUserProfileCreationRequest(UserCreateRequest request);
}
