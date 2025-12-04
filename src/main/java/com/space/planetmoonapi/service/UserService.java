package com.space.planetmoonapi.service;

import com.space.planetmoonapi.dto.UserDTO;
import com.space.planetmoonapi.entity.User;
import com.space.planetmoonapi.exception.DuplicateResourceException;
import com.space.planetmoonapi.exception.ResourceNotFoundException;
import com.space.planetmoonapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDTO getUserById(Long id) {
        log.info("Fetching user by ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        return convertToDTO(user);
    }

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        log.info("Creating new user: {}", userDTO.getUsername());

        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new DuplicateResourceException("User with username '" + userDTO.getUsername() + "' already exists");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEnabled(userDTO.getEnabled() != null ? userDTO.getEnabled() : true);
        user.setAccountNonLocked(userDTO.getAccountNonLocked() != null ? userDTO.getAccountNonLocked() : true);
        user.setRole(userDTO.getRole());

        User savedUser = userRepository.save(user);
        log.info("User created successfully with ID: {}", savedUser.getUserId());

        return convertToDTO(savedUser);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setEnabled(user.getEnabled());
        dto.setAccountNonLocked(user.getAccountNonLocked());
        dto.setRole(user.getRole());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        // Do not include password in DTO for security
        return dto;
    }
}