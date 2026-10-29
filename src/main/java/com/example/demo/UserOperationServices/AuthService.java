package com.example.demo.UserOperationServices;

import com.example.demo.UserOperationsDto.UserRequest;
import com.example.demo.dto.UserDto;
import com.example.demo.entities.User;
import com.example.demo.mappers.UserMapper;
import com.example.demo.services.JwtServices;
import com.example.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtServices jwtServices;

    public User register(UserRequest request) {
        if (userService.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = userMapper.toEntity(request);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setBalance(BigDecimal.ZERO);
        user.setDepositBalance(BigDecimal.ONE);
        return userService.create(user);
    }

    public String login(UserRequest request) {
        User user = userService.findByUsername(request.getUsername());
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid password");
        }
        return jwtServices.generateToken(user);
    }

    public UserDto getUserInfo(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        return userMapper.toDto(user);
    }

}
