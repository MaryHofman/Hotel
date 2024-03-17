package com.example.demo.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.configurations.JWTprovider;
import com.example.demo.exeptions.ExeptionJWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private JWTprovider jwtProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        
        String token = request.getHeader("Authorization");
        int validJWT=jwtProvider.validateAccessToken(token);
        if (token != null && jwtProvider.validateAccessToken(token)==0) {
                Claims claims = jwtProvider.getAccessClaims(token);
                return jwtProvider.validateAccessToken(token)==0;
               
        } else {
           
            if(validJWT==1){
                response.sendError(1, "Unsupported jwt");
                throw new ExeptionJWT("Unsupported jwt");
            }
            else if(validJWT==2){
                response.sendError(2, "Malformed jwt");
                throw new ExeptionJWT("Malformed jwt");
            }
            else{
                response.sendError(3, "invalid token");
                throw new ExeptionJWT("invalid token");
            }
        }
        
    }
}
