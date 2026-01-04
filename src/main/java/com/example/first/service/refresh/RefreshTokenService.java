package com.example.first.service.refresh;

import com.example.first.entity.Token;
import com.example.first.entity.User;

import java.util.Map;
import java.util.Optional;

public interface RefreshTokenService {

    String createRefreshToken(User user);

    Map<String, String> refresh(String authHeader);

    Optional<Token> getValidTokenByUser(User user);

}