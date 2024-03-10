package com.example.demo.reposytories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.enteies.Users;

@Repository
public interface UserRepository extends CrudRepository<Users, Long> {
    Optional<Users> findByEmail(String email); 
    
}
