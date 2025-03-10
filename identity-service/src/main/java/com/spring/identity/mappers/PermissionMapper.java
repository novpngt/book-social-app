package com.spring.identity.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.spring.identity.dtos.requests.PermissionRequest;
import com.spring.identity.dtos.responses.PermissionResponse;
import com.spring.identity.entities.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest permissionRequest);

    PermissionResponse toPermissionResponse(Permission permission);

    void updatePermission(@MappingTarget Permission permission, PermissionRequest permissionRequest);
}
