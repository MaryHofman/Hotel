package com.example.demo.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.HotelCard;
import com.example.demo.DTO.HotelDTO;
import com.example.demo.DTO.InformationAboutHotel;
import com.example.demo.DTO.PartsOfArticle;
import com.example.demo.configurations.JWTprovider;
import com.example.demo.enteies.Extras;
import com.example.demo.enteies.Hotel;
import com.example.demo.enteies.Users;
import com.example.demo.reposytories.HotelRepository;
import com.example.demo.utils.ImageUtil;

import jakarta.transaction.Transactional;

@Service
public class HotelService {

    @Autowired
    private  JWTprovider jwTprovider;
    @Autowired
    private UserService userService;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private ImageUtil imageUtil;
    @Autowired
    private HotelImageService hotelImageService;
    @Autowired
    private ExtrasService extrasService;
   


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
        
        Long countRating = hotel.getRatings_count();
        Long totalRating = hotel.getTotal_rating();
        
        Double rating = ((double) totalRating / countRating)+(totalRating % countRating);
        hotelCard.setRating(rating);
        hotelCard.setGeography(hotel.getGeography());
    
        return hotelCard;
    }


    public ResponseEntity<String> createCard(InformationAboutHotel informationAboutHotel, String jwtToken) throws IOException {
        Hotel hotel = new Hotel();
        hotel.setName(informationAboutHotel.getName());
        hotel.setAddress(informationAboutHotel.getAddress());
        hotel.setDescription(informationAboutHotel.getDescription());
        hotel.setGeography(informationAboutHotel.getGeography());
        hotel.setPrice(informationAboutHotel.getPrice());

        String email = jwTprovider.getAccessClaims(jwtToken).get("firstName").toString();
        Users user=userService.findByUsername(email).get();

        hotel.setUser_id(user.getId());
        String mainPath="/img/hotelIMG";
        String mainURL= imageUtil.saveImage(informationAboutHotel.getMainImg(),mainPath);

        hotel.setImgUrl(mainURL);

        for(MultipartFile file: informationAboutHotel.getAllImgs()){
            String url=imageUtil.saveImage(file, mainPath);
            hotelImageService.saveImg(hotel.getHotel_id(), url);
        }

        Extras extras=new Extras();
        extras.setHotelId(hotel.getHotel_id());
        extras.setStringArray(informationAboutHotel.getExtras());

        extrasService.saveExtras(extras);


        return ResponseEntity.ok("Данные об отеле успешно сохранены!");
    }
    
    
}
