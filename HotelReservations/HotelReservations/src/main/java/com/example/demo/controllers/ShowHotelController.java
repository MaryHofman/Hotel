package com.example.demo.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.example.demo.DTO.HotelCard;
import com.example.demo.DTO.InformationAboutHotel;
import com.example.demo.DTO.MainInformationAboutHotel;
import com.example.demo.DTO.requestFavouriteDTO;
import com.example.demo.DTO.Coordinates;
import com.example.demo.services.FavouriteService;
import com.example.demo.services.HotelService;
import com.example.demo.services.UserService;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ShowHotelController {
    @Autowired
    private HotelService hotelService;

    @Autowired
    private FavouriteService favouriteService;
   
    @GetMapping("/listHotels")
    public ResponseEntity<List<HotelCard>> getMethodName() {
        List<HotelCard> ListArticles = hotelService.GetInformationAboutAllHotels();
        return ResponseEntity.ok(ListArticles) ;
    }

    @GetMapping("/listHotels/takeTheNearest")
    public ResponseEntity<?> getNearest(@RequestParam("latitude") String latitude,
                                        @RequestParam("longitude") String longitude) throws IOException {
        Coordinates coordinates=new Coordinates();
        coordinates.setLatitude(Double.valueOf(latitude));
        coordinates.setLongitude(Double.valueOf(longitude));
        return ResponseEntity.ok(hotelService.takeTheNearest(coordinates));
    }
    

    //список лучших отелей
    @GetMapping("/listHotels/takeTheBest")
    public ResponseEntity<?> getBest() throws IOException {
        return ResponseEntity.ok(hotelService.takeTheBest());
    }

    //получение полной информации об отеле
    @GetMapping("/listHotels/{id}")
public ResponseEntity<MainInformationAboutHotel> getHotelById(@PathVariable Long id) {
    MainInformationAboutHotel hotel = hotelService.getHotelById(id);
    
    if (hotel != null) {
        return ResponseEntity.ok(hotel);
    } else {
        return ResponseEntity.notFound().build();
    }
}

@GetMapping("/listHotels/User/{userId}")
public ResponseEntity<List<HotelCard>> getHotelByUserId(@PathVariable Long userId, @RequestHeader("Authorization") String jwtToken) {
    return ResponseEntity.ok(hotelService.getHotelByUserId(userId));
}

@PostMapping("/listHotels/addFavourite")
public ResponseEntity<?> addFavourite(@RequestHeader("Authorization") String jwtToken, @RequestParam("hotelId") Long hotelId){
    return favouriteService.addFavourite(jwtToken, hotelId);
}

@DeleteMapping("/listHotels/deleteFavourite/{hotelId}")
public ResponseEntity<?> deleteFavourite(@RequestHeader("Authorization") String jwtToken, @PathVariable Long hotelId){
    return favouriteService.deleteFavourite(jwtToken, hotelId);
}

@GetMapping("/listHotels/Favourite/{userId}")
public ResponseEntity<?> getListFavourite(@RequestHeader("Authorization") String jwtToken, @PathVariable Long userId){
    return favouriteService.getListFavourite(jwtToken);
}





}
