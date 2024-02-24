package com.example.demo.services;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.InformationAboutUser;
import com.example.demo.DTO.JwtRequest;
import com.example.demo.DTO.JwtResponse;
import com.example.demo.DTO.RegistrationUserDTO;
import com.example.demo.configurations.JWTprovider;
import com.example.demo.enteies.Role;
import com.example.demo.enteies.Users;
import com.example.demo.reposytories.UserRepository;
import com.example.demo.utils.ImageUtil;

import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor

public class AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTprovider jwtProvider;
    @Autowired
    private final ImageUtil imageUtil;

    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(("Неправильный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }

        Optional<Users> opuser = userService.findByUsername(authRequest.getUsername());
        Users user=opuser.get();
        String firstName=user.getFirstName();
        String secondName=user.getSecondName();
        String email=user.getEmail();
        Collection <Role> role=user.getRoles();

        if (user == null) {
            return new ResponseEntity<>(("Пользователь не найден"), HttpStatus.UNAUTHORIZED);}

        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String userRole = userDetails.getAuthorities().iterator().next().getAuthority();
        String token = jwtProvider.generateAccessToken(user);
        String accessToken = jwtProvider.generateAccessToken(user);
        String refreshToken = jwtProvider.generateRefreshToken(user);
        return ResponseEntity.ok(getInformationAboutUser(user, token, token));
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

        return ResponseEntity.ok(getInformationAboutUser(user, accessToken, refreshToken));
    }


    private InformationAboutUser getInformationAboutUser(Users user, String tokenAccess, String tokenRefresh ){
        InformationAboutUser information=new InformationAboutUser(user.getId(), user.getFirstName(),user.getSecondName(),user.getEmail(),tokenAccess,tokenRefresh);
        if(user.getImgURL()!=null){
            information.setUrlImg(user.getImgURL());
        }

        return information;
    }

    public ResponseEntity<?> changeProfile(InformationAboutUser userProfile) {

        
        Users user=userService.findByUsername(userProfile.getEmail()).get();
        user.setFirstName(userProfile.getFirstName());
        user.setSecondName(userProfile.getSecondName());
        String newEmail=userProfile.getEmail();
        Optional<Users> checkUser=userService.findByUsername(newEmail);
        if(checkUser.isEmpty() ||  checkUser.get().getId()==user.getId()){
            user.setEmail(newEmail);
        }

        Users newUser=userService.saveUpdateUser(user);
        InformationAboutUser information=new InformationAboutUser(newUser.getId(), newUser.getFirstName(), newUser.getSecondName(), newUser.getEmail(), userProfile.getTokenAccess(),userProfile.getTokenRefresh());
        information.setUrlImg(userProfile.getUrlImg());
        return  ResponseEntity.ok(information);
    }

    public ResponseEntity<?> changePhoto(MultipartFile profilePhoto, String accessToken) throws IOException {
        Claims information = jwtProvider.getAccessClaims(accessToken);
        Users user=userService.findByUsername(information.get("email").toString()).get();
        String path="/img/ProfileIMG";
        if(user.getImgURL().equals(null)){   
            String fileName = imageUtil.saveImage(profilePhoto, path);
            return ResponseEntity.ok(userService.saveUpdateUser(user));
        }
        else{
            imageUtil.deleteImage(user.getImgURL(),path);
            String fileName = imageUtil.saveImage(profilePhoto,path);
            return ResponseEntity.ok(userService.saveUpdateUser(user));
        }
       
    }
}