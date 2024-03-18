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
import com.example.demo.exeptions.ExeptionJWT;

@Component
public class CreateInterceptor implements HandlerInterceptor {
    @Autowired
    private JWTprovider jwtProvider;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        int validJWT=jwtProvider.validateAccessToken(token);
        
        if (token != null && jwtProvider.validateAccessToken(token)==0) {
            try {
                Claims claims = jwtProvider.getAccessClaims(token);
                List<Map<String, Object>> roles = (List<Map<String, Object>>) claims.get("roles");
                List<String> roleNames = new ArrayList<>();

                for (Map<String, Object> role : roles) {
                    String roleName = (String) role.get("name");
                    roleNames.add(roleName);
                }

                System.out.println(roleNames);
                
                if(roleNames.contains("AUTHOR") || roleNames.contains("ADMIN")){
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
            if(validJWT==1){
                response.sendError(1, "Unsupported jwt");
                throw new ExeptionJWT("Unsupported jwt");
            }
            else if(validJWT==2){
                response.sendError(2, "Malformed jwt");
                throw new ExeptionJWT("Malformed jwt");
            }
            else if(validJWT==3){
                response.sendError(3, "invalid token");
                throw new ExeptionJWT("invalid token");
            }
            else{response.sendError(4, "null token");
            throw new ExeptionJWT("null  token");}
          
        }
    }
}