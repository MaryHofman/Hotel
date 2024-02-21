package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.demo.DTO.HotelDTO;
import com.example.demo.services.HotelService;

@Controller
public class CreateController {
    @Autowired
    private HotelService articleService;

    @PostMapping("/create")
    public ResponseEntity<String> createArticle(@RequestBody HotelDTO articleDTO,  @RequestHeader("Authorization") String jwtToken) {
        return null;
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteArticle(@RequestBody Long id_article) {
        return null;
    }
    
}
