package org.p4.authentication.configuration;

import org.p4.authentication.model.entity.UserRole;

import java.util.UUID;

public record AuthenticatedUser(
        UUID id,
        String email,
        UserRole role
) {}