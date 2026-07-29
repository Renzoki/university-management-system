package org.p4.academicservice.service;

import io.jsonwebtoken.Claims;
import org.p4.academicservice.model.entity.enums.UserRole;
import org.p4.academicservice.model.entity.enums.UserStatus;

import java.util.UUID;

public interface JwtService {
    Claims extractAllClaims(String token);
    UUID extractUserId(String token);
    String extractUserEmail(String token);
    UserStatus extractStatus(String token);
    UserRole extractRole(String token);
    boolean isTokenExpired(String token);
    boolean isTokenValid(String token);
}
