package org.p4.academicservice.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.p4.academicservice.model.entity.enums.UserRole;
import org.p4.academicservice.model.entity.enums.UserStatus;
import org.p4.academicservice.service.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.secret}")
    private String jwtSecret;

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

    @Override
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

    @Override
    public UserRole extractRole(String token) {
        String role = extractAllClaims(token).get("role", String.class);
        return UserRole.valueOf(role);
    }

    @Override
    public boolean isTokenExpired(String token) {
        Date expiry = extractAllClaims(token).getExpiration();
        Date now = new Date();
        return expiry.before(now);
    }

    @Override
    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return !isTokenExpired(token) && extractStatus(token) != UserStatus.SUSPENDED ;
        } catch (Exception e) {
            return false;
        }
    }
}
