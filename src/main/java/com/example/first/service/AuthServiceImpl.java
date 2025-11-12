package com.example.first.service;


import com.example.first.dto.request.UserDtoRequest;
import com.example.first.dto.response.UserDtoResponse;
import com.example.first.entity.User;
import com.example.first.entity.UserRole;
import com.example.first.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDtoResponse registerUser(UserDtoRequest dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(UserRole.USER);
        User userSaved = userRepository.save(user);

        UserDtoResponse dtoResponse = new UserDtoResponse();
        dtoResponse.setId(userSaved.getId());
        dtoResponse.setUsername(userSaved.getUsername());
        dtoResponse.setEmail(userSaved.getEmail());
        dtoResponse.setRole(userSaved.getRole());

        return dtoResponse;
    }
}
