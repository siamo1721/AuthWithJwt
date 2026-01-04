package com.example.first.service.refresh;

import com.example.first.entity.Token;
import com.example.first.entity.User;
import com.example.first.repository.TokenRepository;
import com.example.first.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final TokenRepository tokenRepository;
    private final long refreshTokenExpiration;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public RefreshTokenServiceImpl(@Value("${jwt.refresh-token.expiration-ms}") long refreshTokenExpiration, TokenRepository tokenRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.tokenRepository = tokenRepository;
        this.refreshTokenExpiration = refreshTokenExpiration;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public String createRefreshToken(User user) {
        String rawToken = UUID.randomUUID().toString();
        Token refreshToken = tokenRepository.findByUser(user)
                .orElseGet(Token::new);

        refreshToken.setUser(user);
        refreshToken.setHashToken(passwordEncoder.encode(rawToken));
        refreshToken.setExpiration(Instant.ofEpochMilli(System.currentTimeMillis() + refreshTokenExpiration));
        refreshToken.setActiveToken(true);

        tokenRepository.save(refreshToken);

        return rawToken;
    }

    @Override
    public Optional<Token> getValidTokenByUser(User user) {
        return tokenRepository.findByUserAndActiveTokenTrue(user)
                .filter(token -> token.getExpiration().isAfter(Instant.now()));
    }

    @Override
    public Map<String, String> refresh(String authHeader) {
        String rawRefreshToken = extractToken(authHeader);

        Token token = tokenRepository.findAll().stream()
                .filter(t -> t.isActiveToken() && t.getExpiration().isAfter(Instant.now()))
                .filter(t -> passwordEncoder.matches(rawRefreshToken, t.getHashToken()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token"));

        User user =  token.getUser();

        String accessToken = jwtService.generateToken(
                user.getId(), user.getRole(), user.getEmail()
        );

        return Map.of("accessToken", accessToken);
    }

    private String extractToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing or invalid refresh token");
        }
        return authHeader.substring(7).trim();
    }
}
