package com.example.first.service;

import com.example.first.dto.response.UserDtoResponse;
import com.example.first.entity.User;
import com.example.first.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public List<UserDtoResponse> getUsers() {
        List<User> users =  userRepository.findAll();
        List<UserDtoResponse> userDtoResponses = new ArrayList<>();
        for(User user : users){
            UserDtoResponse dto = new UserDtoResponse();
            dto.setId(user.getId());
            dto.setUsername(user.getUsername());
            dto.setEmail(user.getEmail());
            dto.setRole(user.getRole());
            userDtoResponses.add(dto);
        }
        return userDtoResponses;
    }
}
