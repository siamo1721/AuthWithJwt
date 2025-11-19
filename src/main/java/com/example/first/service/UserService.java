package com.example.first.service;

import com.example.first.dto.register.response.RegisterDtoResponse;

import java.util.List;

public interface UserService {
    List<RegisterDtoResponse> getUsers();
}
