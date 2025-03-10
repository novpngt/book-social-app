package com.spring.identity.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.identity.dtos.requests.PermissionRequest;
import com.spring.identity.dtos.responses.PermissionResponse;
import com.spring.identity.entities.Permission;
import com.spring.identity.enums.ErrorCode;
import com.spring.identity.exceptions.AppException;
import com.spring.identity.mappers.PermissionMapper;
import com.spring.identity.repositories.PermissionRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {

        if (permissionRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.PERMISSION_ALREADY_EXISTS);
        }

        Permission permission = permissionMapper.toPermission(request);
        return permissionMapper.toPermissionResponse(permissionRepository.save(permission));
    }

    public List<PermissionResponse> getPermissions() {
        var permissions = permissionRepository.findAll();
        return permissions.stream()
                .map(permission -> permissionMapper.toPermissionResponse(permission))
                .toList();
    }

    public String delete(String name) {
        if (!permissionRepository.existsByName(name)) {
            return "Failed";
        }
        permissionRepository.deleteById(name);
        return "Success";
    }
}
