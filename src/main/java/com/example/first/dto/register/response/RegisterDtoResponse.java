package com.example.first.dto.register.response;

import com.example.first.entity.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDtoResponse {
    private Long id;
    private String username;
    private String email;
    private UserRole role;
    private String accessToken;
    private String refreshToken;
}
