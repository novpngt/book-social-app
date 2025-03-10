package com.spring.identity.dtos.requests;

import java.util.Set;

import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @Size(min = 6, max = 20, message = "INVALID_USERNAME")
    String username;

    @Size(min = 6, max = 32, message = "INVALID_PASSWORD")
    String password;

    Set<String> roles;
}
