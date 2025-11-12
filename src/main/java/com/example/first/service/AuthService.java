package com.example.first.service;

import com.example.first.dto.request.UserDtoRequest;
import com.example.first.dto.response.UserDtoResponse;

public interface AuthService {
    UserDtoResponse registerUser(UserDtoRequest dto);

}
