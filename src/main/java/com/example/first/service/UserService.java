package com.example.first.service;

import com.example.first.dto.response.UserDtoResponse;

import java.util.List;

public interface UserService {
    List<UserDtoResponse> getUsers();

}
