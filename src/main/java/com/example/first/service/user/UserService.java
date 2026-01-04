package com.example.first.service.user;

import com.example.first.dto.register.response.RegisterDtoResponse;
import com.example.first.entity.User;

import java.util.List;

public interface UserService {
    List<RegisterDtoResponse> getUsers();
    User getUser(Long id);
}
