package org.p4.academicservice.service;

import io.jsonwebtoken.Claims;
import org.p4.academicservice.model.entity.enums.UserRole;
import org.p4.academicservice.model.entity.enums.UserStatus;

import java.util.UUID;

public interface JwtService {
    /**
     * Extracts all claims from the specified JWT.
     *
     * @param token the JWT to parse
     * @return the claims contained in the token
     */
    Claims extractAllClaims(String token);

    /**
     * Extracts the user identifier from the specified JWT.
     *
     * @param token the JWT to parse
     * @return the unique identifier of the user
     */
    UUID extractUserId(String token);

    /**
     * Extracts the user's email address from the specified JWT.
     *
     * @param token the JWT to parse
     * @return the user's email address
     */
    String extractUserEmail(String token);

    /**
     * Extracts the user's account status from the specified JWT.
     *
     * @param token the JWT to parse
     * @return the user's account status
     */
    UserStatus extractStatus(String token);

    /**
     * Extracts the user's role from the specified JWT.
     *
     * @param token the JWT to parse
     * @return the user's role
     */
    UserRole extractRole(String token);

    /**
     * Determines whether the specified JWT has expired.
     *
     * @param token the JWT to validate
     * @return {@code true} if the token has expired; {@code false} otherwise
     */
    boolean isTokenExpired(String token);

    /**
     * Determines whether the specified JWT is valid.
     *
     * @param token the JWT to validate
     * @return {@code true} if the token is valid; {@code false} otherwise
     */
    boolean isTokenValid(String token);
}
