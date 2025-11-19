package com.example.first.dto.login.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDtoRequest {
    @NotBlank(message = "username не должно быть пустым")
    private String email;
    @NotBlank(message = "password не должно быть пустым")
    private String password;
}
