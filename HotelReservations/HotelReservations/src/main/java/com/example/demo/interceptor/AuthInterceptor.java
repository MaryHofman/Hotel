package com.example.demo.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.configurations.JWTprovider;

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

        if (token != null) {
                Claims claims = jwtProvider.getAccessClaims(token);
                return jwtProvider.validateAccessToken(token);
               
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token missing");
            return false;
        }
        
    }
}
