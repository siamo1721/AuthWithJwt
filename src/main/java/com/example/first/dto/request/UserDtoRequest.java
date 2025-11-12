package com.example.first.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDtoRequest {
    @NotBlank(message = "Это поле не должно быть пустым")
    @Email(message = "Неверный формат email")
    private String email;
    @NotBlank(message = "Это поле не должно быть пустым")
    private String username;
    @NotBlank(message = "Это поле не должно быть пустым")
    @Size(min = 8, message = "Пароль должен быть длиннее 8 символов!")
    private String password;
}

