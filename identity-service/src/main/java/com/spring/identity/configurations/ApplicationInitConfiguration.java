package com.spring.identity.configurations;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.spring.identity.entities.Role;
import com.spring.identity.entities.User;
import com.spring.identity.repositories.RoleRepository;
import com.spring.identity.repositories.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfiguration {
    private static final Logger log = LoggerFactory.getLogger(ApplicationInitConfiguration.class);
    RoleRepository roleRepository;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Bean
    @ConditionalOnProperty(
            prefix = "spring.datasource",
            value = "driver-class-name",
            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner init() {
        return args -> {
            log.info("Running ApplicationRunner for role initialization...");
            Role adminRole = roleRepository.findById("ADMIN").orElseGet(() -> {
                Role newRole =
                        Role.builder().name("ADMIN").description("ADMIN ROLE").build();
                return roleRepository.save(newRole);
            });
            Role userRole = roleRepository.findById("USER").orElseGet(() -> {
                Role newRole =
                        Role.builder().name("USER").description("USER ROLE").build();
                return roleRepository.save(newRole);
            });
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = User.builder()
                        .username("admin")
                        .roles(Set.of(adminRole))
                        .password(passwordEncoder.encode("admin"))
                        .build();
                userRepository.save(admin);
                log.warn(
                        "admin user has been created (default password: admin). {}. Please change password immediately!!!",
                        admin);
            }
        };
    }
}
