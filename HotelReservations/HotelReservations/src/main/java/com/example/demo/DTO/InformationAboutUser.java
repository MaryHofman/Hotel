package com.example.demo.DTO;

import java.util.Collection;

import com.example.demo.enteies.Role;

public class InformationAboutUser{
    private Long id;
    private String firstName;
    private String SecondName;
    private String email;
    private String urlImg;
    private String tokenAccess;
    private String tokenRefresh;
    private Collection<Role> roles;

  
    
    public InformationAboutUser(Long id, String firstName, String secondName, String email, 
            String tokenAccess, String tokenRefresh, Collection<Role> roles) {
        this.id = id;
        this.firstName = firstName;
        this.SecondName = secondName;
        this.email = email;
        this.tokenAccess = tokenAccess;
        this.tokenRefresh = tokenRefresh;
        this.roles = roles;
    }

   

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return SecondName;
    }

    public void setSecondName(String secondName) {
        SecondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTokenAccess() {
        return tokenAccess;
    }

    public void setTokenAccess(String tokenAccess) {
        this.tokenAccess = tokenAccess;
    }

    public String getTokenRefresh() {
        return tokenRefresh;
    }

    public void setTokenRefresh(String tokenRefresh) {
        this.tokenRefresh = tokenRefresh;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    
    

    
}