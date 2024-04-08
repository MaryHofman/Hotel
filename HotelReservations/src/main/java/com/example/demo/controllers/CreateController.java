package com.example.demo.controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;
import com.example.demo.DTO.HotelCard;
import com.example.demo.DTO.HotelDTO;
import com.example.demo.DTO.IdWrapper;
import com.example.demo.DTO.InformationAboutHotel;
import com.example.demo.enteies.Hotel;
import com.example.demo.services.HotelService;
import org.springframework.util.Base64Utils;


@Controller
public class CreateController {
    @Autowired
    private HotelService hotelService;
    
    @PostMapping("/create")
    public ResponseEntity<?> createHotel(@RequestBody InformationAboutHotel informationAboutHotel, @RequestHeader("Authorization") String jwtToken) throws IOException {
        return hotelService.createCard(informationAboutHotel, jwtToken);
    }

    @GetMapping("/getHotelsOfUser")
    public ResponseEntity<List<HotelCard>> getListHotelsOfUser(@RequestHeader("Authorization") String jwtToken) {
        List<HotelCard> hotels = hotelService.listHotelsOfUser(jwtToken);
        return ResponseEntity.ok().body(hotels);
    }
    

    @DeleteMapping("/delete/{hotelId}")
    public ResponseEntity<?> deleteHotel(@PathVariable Long hotelId, @RequestHeader("Authorization") String jwtToken) throws IOException {
        return hotelService.deleteInformationAboutHotel(hotelId, jwtToken);
    }
    

    
}
