package com.example.first.service.user;

import com.example.first.dto.register.response.RegisterDtoResponse;
import com.example.first.entity.User;
import com.example.first.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public List<RegisterDtoResponse> getUsers() {
        List<User> users =  userRepository.findAll();
        List<RegisterDtoResponse> registerDtoResponse = new ArrayList<>();
        for(User user : users){
            RegisterDtoResponse dto = new RegisterDtoResponse();
            dto.setId(user.getId());
            dto.setUsername(user.getUsername());
            dto.setEmail(user.getEmail());
            dto.setRole(user.getRole());
            registerDtoResponse.add(dto);
        }
        return registerDtoResponse;
    }
    public User getUser(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Нет user с id " + id));
    }
}
