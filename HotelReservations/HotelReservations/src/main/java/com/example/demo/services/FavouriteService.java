package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.HotelCard;
import com.example.demo.DTO.requestFavouriteDTO;
import com.example.demo.configurations.JWTprovider;
import com.example.demo.enteies.Favourite;
import com.example.demo.enteies.Hotel;
import com.example.demo.enteies.Users;
import com.example.demo.reposytories.FavouriteRepository;

import io.jsonwebtoken.Claims;

@Service
public class FavouriteService {
    @Autowired
    private JWTprovider jwtProvider;
    @Autowired
    private UserService userService; 
    @Autowired
    private HotelService hotelService; 
    @Autowired
    private FavouriteRepository favouriteRepository; 

    public ResponseEntity<?> addFavourite(String jwtToken, Long hotelId) {
        Claims claims = jwtProvider.getAccessClaims(jwtToken);
        String email = claims.get("email").toString();
        Users user=userService.findByUsername(email).get();

        Favourite favorite=new Favourite();
        favorite.setUser(user);
        favorite.setHotel(hotelService.findHotelById(hotelId));

        favouriteRepository.save(favorite);

        return ResponseEntity.ok("Рекомендация сохранена");
    }

    public ResponseEntity<?> deleteFavourite(String jwtToken, Long hotelId) {

        Claims claims = jwtProvider.getAccessClaims(jwtToken);
        String email = claims.get("email").toString();
        Users user=userService.findByUsername(email).get();

        favouriteRepository.deleteByUserAndHotel(user, hotelService.findHotelById(hotelId));
        return ResponseEntity.ok("Рекомендация удалена");
    }

    public ResponseEntity<?> getListFavourite(String jwtToken) {
        Claims claims = jwtProvider.getAccessClaims(jwtToken);
        String email = claims.get("email").toString();
        Users user=userService.findByUsername(email).get();
        List<Favourite> favorite=favouriteRepository.findByUser(user);

        List<Hotel> hotels=new ArrayList<>();

        for(Favourite f: favorite){
            hotels.add(hotelService.findHotelById(f.getHotel().getHotelId()));
        }

        List<HotelCard> hotelCard=new ArrayList<>();

        for(Hotel h: hotels){
            hotelCard.add(hotelService.convertToHotelCard(h));
        }
        return ResponseEntity.ok(hotelCard);
    }
    
}
