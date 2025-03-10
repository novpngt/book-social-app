package com.spring.identity.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.spring.identity.dtos.requests.UserProfileCreationRequest;
import com.spring.identity.mappers.ProfileMapper;
import com.spring.identity.repositories.httpClients.UserProfileClient;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.identity.dtos.requests.UserCreateRequest;
import com.spring.identity.dtos.requests.UserUpdateRequest;
import com.spring.identity.dtos.responses.UserResponse;
import com.spring.identity.entities.Role;
import com.spring.identity.entities.User;
import com.spring.identity.enums.ErrorCode;
import com.spring.identity.exceptions.AppException;
import com.spring.identity.mappers.UserMapper;
import com.spring.identity.repositories.RoleRepository;
import com.spring.identity.repositories.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    UserProfileClient userProfileClient;
    ProfileMapper profileMapper;
    KafkaTemplate<String, String> kafkaTemplate;

    public UserResponse createUser(UserCreateRequest request) {
        User user = userMapper.toUser(request);
        Role role = roleRepository.findById("USER").orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        user.setRoles(Set.of(role));
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        try {
            user = userRepository.save(user);
            UserProfileCreationRequest userProfileCreationRequest = profileMapper.toUserProfileCreationRequest(request);
            userProfileCreationRequest.setUserId(user.getId());
            userProfileClient.createUserProfile(userProfileCreationRequest);
            // publish message to kafka
            kafkaTemplate.send("onboard-successful", "Welcome: ", user.getUsername());
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }

        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUserById(String id) {
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
    }

    public UserResponse getMyInfo() {
        SecurityContext context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(String id, UserUpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        var roles = roleRepository.findAllById(request.getRoles());

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(new HashSet<>(roles));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public String deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userRepository.delete(user);
        return "deleted";
    }
}
