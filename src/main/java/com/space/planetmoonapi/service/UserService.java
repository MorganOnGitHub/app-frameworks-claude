package com.space.planetmoonapi.service;

import com.space.planetmoonapi.dto.UserDTO;
import com.space.planetmoonapi.dto.UserSummaryDTO;
import com.space.planetmoonapi.entity.User;
import com.space.planetmoonapi.enums.UserRole;
import com.space.planetmoonapi.exception.DuplicateResourceException;
import com.space.planetmoonapi.exception.ResourceNotFoundException;
import com.space.planetmoonapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        log.info("Creating new user: {}", userDTO.getUsername());

        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new DuplicateResourceException("User with username '" + userDTO.getUsername() + "' already exists");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEnabled(userDTO.getEnabled());
        user.setActive(userDTO.getActive());
        user.setRole(userDTO.getRole());

        User savedUser = userRepository.save(user);
        log.info("User created successfully with ID: {}", savedUser.getUserId());

        return convertToDTO(savedUser);
    }

    public List<UserDTO> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        log.info("Fetching user by ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        return convertToDTO(user);
    }

    public UserDTO getUserByUsername(String username) {
        log.info("Fetching user by username: {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        return convertToDTO(user);
    }

    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        log.info("Updating user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        if (!user.getUsername().equals(userDTO.getUsername()) &&
                userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new DuplicateResourceException("User with username '" + userDTO.getUsername() + "' already exists");
        }

        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEnabled(userDTO.getEnabled());
        user.setActive(userDTO.getActive());
        user.setRole(userDTO.getRole());

        User updatedUser = userRepository.save(user);
        log.info("User updated successfully with ID: {}", updatedUser.getUserId());

        return convertToDTO(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        log.info("Deleting user with ID: {}", id);

        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }

        userRepository.deleteById(id);
        log.info("User deleted successfully with ID: {}", id);
    }

    public List<UserDTO> getUsersByRole(UserRole role) {
        log.info("Fetching users by role: {}", role);
        return userRepository.findByRole(role).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getEnabledUsers() {
        log.info("Fetching enabled users");
        return userRepository.findByEnabled(true).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<UserSummaryDTO> getAllUserSummaries() {
        log.info("Fetching all user summaries");
        return userRepository.findAllUserSummaries();
    }

    public UserSummaryDTO getUserSummaryById(Long id) {
        log.info("Fetching user summary by ID: {}", id);
        return userRepository.findUserSummaryById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }

    public List<UserSummaryDTO> getUserSummariesByRole(UserRole role) {
        log.info("Fetching user summaries by role: {}", role);
        return userRepository.findUserSummariesByRole(role);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setEnabled(user.getEnabled());
        dto.setActive(user.getActive());
        dto.setRole(user.getRole());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
}