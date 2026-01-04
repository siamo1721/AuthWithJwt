package com.example.first.security.jwt;

import com.example.first.entity.UserRole;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface JwtService {
    String generateToken(Long userId, UserRole role, String email);

    boolean isValidToken(String token);

    Long extractUserId(String token);

    String extractRole(String token);

    List<GrantedAuthority> getAuthorities(String role);
}
