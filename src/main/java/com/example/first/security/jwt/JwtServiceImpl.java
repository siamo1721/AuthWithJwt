package com.example.first.security.jwt;

import com.example.first.entity.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

    private final String secret;
    private final long accessTokenExpiration;

    public JwtServiceImpl(@Value("${jwt.secret}") String secret,
                          @Value("${jwt.access-token.expiration-ms}") long accessTokenExpiration
    ) {
        this.secret = secret;
        this.accessTokenExpiration = accessTokenExpiration;
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    @Override
    public String generateToken(Long userId, UserRole role, String email) {
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("role", role)
                .claim("email", email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(getSigningKey())
                .compact();
    }

    @Override
    public boolean isValidToken(String token) {
       log.info(token);
        try {
            Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
        } catch (JwtException e) {
            log.info(String.valueOf(e));
            return false;
        }
        return true;
    }

    @Override
    public Long extractUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    @Override
    public String extractRole(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("role", String.class);
    }

    @Override
    public List<GrantedAuthority> getAuthorities(String role) {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }
}
