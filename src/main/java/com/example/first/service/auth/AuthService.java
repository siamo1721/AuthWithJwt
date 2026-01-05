package com.example.first.service.auth;

import com.example.first.dto.login.request.LoginDtoRequest;
import com.example.first.dto.register.request.RegisterDtoRequest;
import com.example.first.dto.register.response.RegisterDtoResponse;
import com.example.first.entity.Token;

import java.util.Optional;

public interface AuthService {
    RegisterDtoResponse registerUser(RegisterDtoRequest dto);
    RegisterDtoResponse loginUser(LoginDtoRequest dto);
    Optional<Token> logoutUser(Long userId);
}
