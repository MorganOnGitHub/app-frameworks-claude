package com.space.planetmoonapi.graphql;

import com.space.planetmoonapi.enums.UserRole;

public record CreateUserInput(
        String username,
        String password,
        Boolean enabled,
        Boolean accountNonLocked,
        UserRole role
) {}