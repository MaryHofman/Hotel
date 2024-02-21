package com.example.demo.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.configurations.JWTprovider;
import com.example.demo.enteies.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private JWTprovider jwtProvider;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        System.out.println(token);
        if (token != null) {
            try {
                Claims claims = jwtProvider.getAccessClaims(token);
                System.out.println("hello"+claims);
                List<Map<String, Object>> roles = (List<Map<String, Object>>) claims.get("roles");
                List<String> roleNames = new ArrayList<>();

                for (Map<String, Object> role : roles) {
                    String roleName = (String) role.get("name");
                    roleNames.add(roleName);
                }

                System.out.println(roleNames);
                
                if(roleNames.contains("USER")){
                System.out.println("hello");
                String firstName = (String) claims.get("firstName");
                request.setAttribute("username", firstName);
                return true;}
                else{
                    return false;
                }         
            } catch (ExpiredJwtException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
                return false;
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return false;
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token missing");
            return false;
        }
    }
}