package com.example.demo.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.enteies.HotelImage;
import com.example.demo.reposytories.HotelImageRepository;
import com.example.demo.reposytories.HotelRepository;
import com.example.demo.utils.ImageUtil;

@Service
public class HotelImageService {

    @Autowired
    private HotelImageRepository hotelImageRepository;
    @Autowired
    private ImageUtil imageUtil;

    public void saveImg(Long hotel_id, String url) {
        HotelImage hotelImage=new HotelImage();
        hotelImage.setHotelId(hotel_id);
        hotelImage.setImageUrl(url);
        HotelImage s=hotelImageRepository.save(hotelImage);}

    public void deleteImg(Long hotel_id) throws IOException {
            List<HotelImage> list_of_images=hotelImageRepository.findByHotelId(hotel_id);
            for (HotelImage hotelImage : list_of_images) {
                imageUtil.deleteImage("img/hotelIMG", hotelImage.getImageUrl());}
            hotelImageRepository.deleteAllImagesByHotelId(hotel_id);}


    public List<String> getAllImagesURL(Long hotelId){

        List<HotelImage> hotelImg=hotelImageRepository.findByHotelId(hotelId);

        if(! hotelImg.isEmpty()){
        List<String> stringHotelImg=new ArrayList<>();
        for(HotelImage img: hotelImg){
            stringHotelImg.add(img.getImageUrl());
        }
        return stringHotelImg;}

        return null;
    }

    
    
}
