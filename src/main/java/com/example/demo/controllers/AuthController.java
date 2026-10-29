package com.example.demo.controllers;

import com.example.demo.UserOperationsDto.UserRequest;
import com.example.demo.UserOperationServices.AuthService;
import com.example.demo.dto.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/users")
    public String createUser(@Valid @RequestBody UserRequest request) {
        authService.register(request);
        return "Your account was created";
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody UserRequest request) {
        return "Welcome, " + request.getUsername() + ". Your token is: " + authService.login(request);
    }

    @GetMapping("/info")
    public UserDto info(Authentication authentication) {
        return authService.getUserInfo(authentication);
    }

}
