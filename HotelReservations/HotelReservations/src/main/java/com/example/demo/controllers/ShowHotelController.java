package com.example.demo.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.example.demo.DTO.GeoIP;
import com.example.demo.DTO.HotelCard;
import com.example.demo.services.HotelService;
import com.example.demo.services.UserService;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;

import jakarta.servlet.http.HttpServletRequest;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/listHotels/takeTheNearest/{ipAddress}")
    public ResponseEntity<?> getNearestById(@PathVariable GeoIP geo, HttpServletRequest request
    ) throws IOException, GeoIp2Exception {
        return ResponseEntity.ok(hotelService.takeTheNearest(geo, request));
    }
    
}
