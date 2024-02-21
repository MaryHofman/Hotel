package com.example.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.JwtRequest;
import com.example.demo.DTO.RegistrationUserDTO;
import com.example.demo.reposytories.UserRepository;
import com.example.demo.services.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor

public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;
  

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) { 
        return authService.createAuthToken(authRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDTO registrationUserDto) {
        return authService.createNewUser(registrationUserDto);
    }
}
