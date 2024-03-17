package com.example.demo.services;

import org.springframework.stereotype.Service;
import com.example.demo.enteies.Role;
import com.example.demo.reposytories.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole(String role) {
        return roleRepository.findByName(role).get();
    }
}