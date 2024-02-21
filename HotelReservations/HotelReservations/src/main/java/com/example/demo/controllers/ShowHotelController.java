package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.example.demo.DTO.HotelCard;
import com.example.demo.services.HotelService;
import com.example.demo.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ShowHotelController {
    @Autowired
    private HotelService hotelService;
    

    @GetMapping("/listHotels")
    public ResponseEntity<List<HotelCard>> getMethodName() {
        List<HotelCard> ListArticles = hotelService.GetInformationAboutAllHotels();
        return ResponseEntity.ok(ListArticles) ;
    }
    
}
