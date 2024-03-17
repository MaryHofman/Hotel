package com.example.demo.controllers;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.InformationAboutUser;
import com.example.demo.DTO.JwtRequest;
import com.example.demo.DTO.RefreshTokenRequest;
import com.example.demo.DTO.RegistrationUserDTO;
import com.example.demo.reposytories.UserRepository;
import com.example.demo.services.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor

public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;
  
    //авторизация
    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) { 
        return authService.createAuthToken(authRequest);
    }
    //регистрация
    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDTO registrationUserDto) {
        return authService.createNewUser(registrationUserDto);
    }
    //изменение информации в профиле пользователя
    @PutMapping("/profile")
    public ResponseEntity<?> editUserProfile(@RequestBody InformationAboutUser userProfile) {
        return authService.changeProfile(userProfile);
    }
    // изменение фотографии пользователя
    @PutMapping("/changePhoto")
    public ResponseEntity<?> changePhoto(@RequestBody String  profilePhoto,   @RequestHeader(name = "Authorization") String accessToken) throws IOException {
        return authService.changePhoto(profilePhoto,accessToken);
    }
    //проверка access токена
    @PostMapping("/checkToken")
    public ResponseEntity<?> checkToken(@RequestHeader(name = "Authorization") String accessToken){
        return authService.checkValidToken(accessToken);
    }
    //получение нового access токена по refresh токену
    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshAccessToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
          System.out.println("Refresh token: "+refreshTokenRequest.getRefreshToken());
        return authService.refreshAccessToken(refreshTokenRequest);
}



}
