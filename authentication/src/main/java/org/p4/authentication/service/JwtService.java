package org.p4.authentication.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.p4.authentication.model.entity.User;
import org.p4.authentication.model.entity.UserRole;

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
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(sub.toString())
                .claim("role", role.name())
                .claim("email", email)
                .issuedAt(
                        Date.from(now)
                )
                .expiration(
                        Date.from(now.plus(expiration))
                )
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret)))
                .compact();
    }
}
