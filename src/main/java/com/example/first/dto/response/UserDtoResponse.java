package com.example.first.dto.response;

import com.example.first.entity.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDtoResponse {
    private Long id;
    private String username;
    private String email;
    private UserRole role;
}
