package com.spring.identity.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.spring.identity.dtos.requests.PermissionRequest;
import com.spring.identity.dtos.responses.ApiResponse;
import com.spring.identity.dtos.responses.PermissionResponse;
import com.spring.identity.services.PermissionService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/permissions")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    ApiResponse<PermissionResponse, Void> addPermission(@RequestBody PermissionRequest permissionRequest) {
        return ApiResponse.<PermissionResponse, Void>builder()
                .result(permissionService.create(permissionRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<PermissionResponse>, Void> getPermissions() {
        return ApiResponse.<List<PermissionResponse>, Void>builder()
                .result(permissionService.getPermissions())
                .build();
    }

    @DeleteMapping("/{permissionId}")
    ApiResponse<String, Void> delete(@PathVariable String permissionId) {
        return ApiResponse.<String, Void>builder()
                .result(permissionService.delete(permissionId))
                .build();
    }
}
