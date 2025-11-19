package com.example.first.controller;

import com.example.first.dto.login.request.LoginDtoRequest;
import com.example.first.dto.register.request.RegisterDtoRequest;
import com.example.first.dto.register.response.RegisterDtoResponse;
import com.example.first.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterDtoResponse> register(@Valid @RequestBody RegisterDtoRequest dtoRequest) {
        RegisterDtoResponse dtoResponse = authService.registerUser(dtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoResponse);
    }
    @PostMapping("/login")
    public ResponseEntity<RegisterDtoResponse> login(@Valid @RequestBody LoginDtoRequest dtoRequest){
        RegisterDtoResponse dtoResponse = authService.loginUser(dtoRequest);
        return ResponseEntity.status(HttpStatus.OK).body(dtoResponse);
    }
}
