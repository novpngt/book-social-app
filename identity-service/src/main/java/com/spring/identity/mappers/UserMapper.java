package com.spring.identity.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.spring.identity.dtos.requests.UserCreateRequest;
import com.spring.identity.dtos.requests.UserUpdateRequest;
import com.spring.identity.dtos.responses.UserResponse;
import com.spring.identity.entities.User;

@Mapper(componentModel = "Spring")
public interface UserMapper {
    User toUser(UserCreateRequest request);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

    UserResponse toUserResponse(User user);
}
