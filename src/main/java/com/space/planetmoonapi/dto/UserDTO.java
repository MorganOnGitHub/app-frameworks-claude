package com.space.planetmoonapi.dto;

import com.space.planetmoonapi.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long userId;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "Enabled status is required")
    private Boolean enabled;

    @NotNull(message = "Active status is required")
    private Boolean active;

    @NotNull(message = "User role is required")
    private UserRole role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}