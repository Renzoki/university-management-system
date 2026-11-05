package org.p4.authentication.service;

import io.jsonwebtoken.Claims;
import org.p4.authentication.model.entity.UserStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.p4.authentication.model.entity.User;
import org.p4.authentication.model.entity.UserRole;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${expiration.hours}")
    private Duration expiration;

    public String generateToken(User user) {
        UUID sub = user.getId();
        UserRole role = user.getRole();
        String email = user.getEmail();
        UserStatus status = user.getStatus();
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(sub.toString())
                .claim("role", role.name())
                .claim("email", email)
                .claim("status", status)
                .issuedAt(
                        Date.from(now)
                )
                .expiration(
                        Date.from(now.plus(expiration))
                )
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret)))
                .compact();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public UUID extractUserId(String token) {
        String id = extractAllClaims(token).getSubject();
        return UUID.fromString(id);
    }

    public String extractUserEmail(String token) {
        return extractAllClaims(token).get("email", String.class);
    }

    public UserStatus extractStatus(String token) {
        String status = extractAllClaims(token).get("status", String.class);
        return UserStatus.valueOf(status);
    }

    public UserRole extractRole(String token) {
        String role = extractAllClaims(token).get("role", String.class);
        return UserRole.valueOf(role);
    }

    public boolean isTokenExpired(String token) {
        Date expiry = extractAllClaims(token).getExpiration();
        Date now = new Date();
        return expiry.before(now);
    }

    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return !isTokenExpired(token) && extractStatus(token) != UserStatus.SUSPENDED ;
        } catch (Exception e) {
            return false;
        }
    }
}
