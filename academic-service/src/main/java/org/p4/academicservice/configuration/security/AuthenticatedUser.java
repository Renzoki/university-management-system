package org.p4.academicservice.configuration.security;

import org.p4.academicservice.model.entity.enums.UserRole;

import java.util.UUID;

public record AuthenticatedUser(
        UUID id,
        String email,
        UserRole role
) {}