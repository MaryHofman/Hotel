package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.enteies.HotelImage;
import com.example.demo.reposytories.HotelImageRepository;
import com.example.demo.reposytories.HotelRepository;

@Service
public class HotelImageService {

    @Autowired
    private HotelImageRepository hotelImageRepository;

    public void saveImg(Long hotel_id, String url) {
        HotelImage hotelImage=new HotelImage();
        hotelImage.setHotelId(hotel_id);
        hotelImage.setImageUrl(url);
        HotelImage s=hotelImageRepository.save(hotelImage);}
    
}
