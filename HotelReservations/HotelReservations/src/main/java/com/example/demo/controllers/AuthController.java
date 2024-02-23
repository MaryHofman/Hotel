package com.example.demo.controllers;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.InformationAboutUser;
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
  

    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) { 
        return authService.createAuthToken(authRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDTO registrationUserDto) {
        return authService.createNewUser(registrationUserDto);
    }

    @PutMapping("/profile")
    public ResponseEntity<?> editUserProfile(@RequestBody InformationAboutUser userProfile) {
        return authService.changeProfile(userProfile);
    }

    @PutMapping("/changePhoto")
    public ResponseEntity<?> changePhoto(@RequestBody MultipartFile  profilePhoto,   @RequestHeader(name = "Authorization") String accessToken) throws IOException {
        return authService.changePhoto(profilePhoto,accessToken);
    }




}
