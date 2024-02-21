package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.HotelCard;
import com.example.demo.DTO.HotelDTO;
import com.example.demo.DTO.PartsOfArticle;
import com.example.demo.configurations.JWTprovider;
import com.example.demo.enteies.Hotel;
import com.example.demo.enteies.Users;
import com.example.demo.reposytories.HotelRepository;


import jakarta.transaction.Transactional;

@Service
public class HotelService {

    @Autowired
    private  JWTprovider jwTprovider;
    @Autowired
    private UserService userService;
    @Autowired
    private HotelRepository hotelRepository;
   


    @Transactional
    public ResponseEntity<String> createHotel(HotelDTO articleDTO, String jwtToken) {
       String email = jwTprovider.getAccessClaims(jwtToken).get("firstName").toString();
       Users user = userService.findByUsername(email).get();
       Hotel article = new Hotel();
       
        return ResponseEntity.ok("Отель успешно добавлен");
    }


    @Transactional
    public List<HotelCard> GetInformationAboutAllHotels() {
        Iterable<Hotel> hotelsIterable = hotelRepository.findAll(); 
        List<Hotel> hotels = new ArrayList<>();
        hotelsIterable.forEach(hotels::add);
        return hotels.stream().map(this::convertToHotelCard).collect(Collectors.toList());
    }

    private HotelCard convertToHotelCard(Hotel hotel) {
        HotelCard hotelCard = new HotelCard();
        hotelCard.setName(hotel.getName());
        hotelCard.setAddress(hotel.getAddress());
        hotelCard.setImgUrl(hotel.getImgUrl());
        hotelCard.setPrice(hotel.getPrice());
        Long count_raiting=hotel.getRatings_count();
        Double total_raiting=hotel.getTotal_rating();
        hotelCard.setRating(total_raiting/count_raiting);
        hotelCard.setGeography(hotel.getGeography());
        return hotelCard;
    }
    
    
}
