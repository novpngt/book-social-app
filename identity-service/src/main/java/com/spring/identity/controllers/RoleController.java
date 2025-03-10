package com.spring.identity.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.spring.identity.dtos.requests.RoleRequest;
import com.spring.identity.dtos.responses.ApiResponse;
import com.spring.identity.dtos.responses.RoleResponse;
import com.spring.identity.services.RoleService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/roles")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @PostMapping()
    ApiResponse<RoleResponse, Void> create(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse, Void>builder()
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>, Void> getRoles() {
        return ApiResponse.<List<RoleResponse>, Void>builder()
                .result(roleService.getRoles())
                .build();
    }

    @DeleteMapping("/{roleId}")
    ApiResponse<String, Void> delete(@PathVariable String roleId) {
        return ApiResponse.<String, Void>builder()
                .result(roleService.delete(roleId))
                .build();
    }
}
