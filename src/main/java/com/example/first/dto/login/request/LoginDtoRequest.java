package com.example.first.dto.login.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDtoRequest {
    @NotBlank(message = "username не должно быть пустым")
    private String email;
    @NotBlank(message = "password не должно быть пустым")
    @Min(message = "Пароль должен быть не менее, чем из 8 символов", value = 8)
    private String password;
}
