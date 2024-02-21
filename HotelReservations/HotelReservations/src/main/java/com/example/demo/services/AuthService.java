package com.example.demo.services;

import java.util.Collection;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.DTO.JwtRequest;
import com.example.demo.DTO.JwtResponse;
import com.example.demo.DTO.RegistrationUserDTO;
import com.example.demo.configurations.JWTprovider;
import com.example.demo.enteies.Role;
import com.example.demo.enteies.Users;
import com.example.demo.reposytories.UserRepository;


import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor

public class AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTprovider jwtProvider;

    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(("Неправильный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }

        Optional<Users> opuser = userService.findByUsername(authRequest.getUsername());
        Users user=opuser.get();
        String username=user.getUsername();
        String email=user.getEmail();
        Collection <Role> role=user.getRoles();

        if (user == null) {
            return new ResponseEntity<>(("Пользователь не найден"), HttpStatus.UNAUTHORIZED);}

        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String userRole = userDetails.getAuthorities().iterator().next().getAuthority();
        String token = jwtProvider.generateAccessToken(user);
        String accessToken = jwtProvider.generateAccessToken(user);
        String refreshToken = jwtProvider.generateRefreshToken(user);
        return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken));
    }

    public JwtResponse refresh(@NonNull String refreshToken) throws Exception {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final Users user = userService.findByUsername(login).get();
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String newRefreshToken = jwtProvider.generateRefreshToken(user);
            return new JwtResponse(accessToken, newRefreshToken);
            }
        throw new Exception("Невалидный JWT токен");
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final Users user = userService.findByUsername(login).get();
            final String accessToken = jwtProvider.generateAccessToken(user);
            return new JwtResponse(accessToken, null);
            
        }
        return new JwtResponse(null, null);
    }

    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDTO registrationUserDto) {
        
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(("Пароли не совпадают"), HttpStatus.BAD_REQUEST);
        }
        if (userService.findByUsername(registrationUserDto.getEmail()).isPresent()) {
            return new ResponseEntity<>(("Пользователь с указанным именем уже существует"), HttpStatus.BAD_REQUEST);
        }
        Users user = userService.createNewUser(registrationUserDto);
        UserDetails userDetails = userService.loadUserByUsername(user.getEmail());
        String userRole = userDetails.getAuthorities().iterator().next().getAuthority();
       

        final String accessToken = jwtProvider.generateAccessToken(user);
        final String refreshToken = jwtProvider.generateRefreshToken(user);

        return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken));
    }
}