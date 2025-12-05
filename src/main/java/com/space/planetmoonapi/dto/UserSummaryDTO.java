package com.space.planetmoonapi.dto;

import com.space.planetmoonapi.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSummaryDTO {

    private Long userId;

    @NotBlank(message = "Username is required")
    private String username;

    @NotNull(message = "User role is required")
    private UserRole role;

    @NotNull(message = "Enabled status is required")
    private Boolean enabled;

    @NotNull(message = "Active status is required")
    private Boolean active;
}