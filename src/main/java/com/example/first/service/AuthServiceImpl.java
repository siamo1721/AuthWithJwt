package com.example.first.service;


import com.example.first.dto.login.request.LoginDtoRequest;
import com.example.first.dto.register.request.RegisterDtoRequest;
import com.example.first.dto.register.response.RegisterDtoResponse;
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
    public RegisterDtoResponse registerUser(RegisterDtoRequest dto) {
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

        return toDto(userSaved);
    }

    @Override
    public RegisterDtoResponse loginUser(LoginDtoRequest dto) {
        User user = userRepository.findByEmail(dto.getEmail());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email is not found");
        }
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong Password");
        }

        return toDto(user);
    }

    private RegisterDtoResponse toDto(User user) {
        RegisterDtoResponse dtoResponse = new RegisterDtoResponse();
        dtoResponse.setId(user.getId());
        dtoResponse.setUsername(user.getUsername());
        dtoResponse.setEmail(user.getEmail());
        dtoResponse.setRole(user.getRole());
        return dtoResponse;
    }
}
