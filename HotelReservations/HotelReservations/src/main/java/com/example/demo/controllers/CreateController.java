package com.example.demo.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.demo.DTO.HotelDTO;
import com.example.demo.DTO.InformationAboutHotel;
import com.example.demo.enteies.Hotel;
import com.example.demo.services.HotelService;

@Controller
public class CreateController {
    @Autowired
    private HotelService hotelService;

    @PostMapping("/create")
    public ResponseEntity<?> createArticle(@RequestBody InformationAboutHotel informationAboutHotel,  @RequestHeader("Authorization") String jwtToken) throws IOException {
        return hotelService.createCard(informationAboutHotel, jwtToken);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteArticle(@RequestBody Long id_article) {
        return null;
    }
    
}
