package com.example.demo.DTO;

import lombok.Data;

@Data
public class RegistrationUserDTO {
    private String firstName;
    private String secondName;
    private String password;
    private String confirmPassword;
    private String email;
    private String role;

}

