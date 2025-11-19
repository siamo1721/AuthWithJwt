package com.example.first.service;

import com.example.first.dto.login.request.LoginDtoRequest;
import com.example.first.dto.register.request.RegisterDtoRequest;
import com.example.first.dto.register.response.RegisterDtoResponse;

public interface AuthService {
    RegisterDtoResponse registerUser(RegisterDtoRequest dto);
    RegisterDtoResponse loginUser(LoginDtoRequest dto);
}
