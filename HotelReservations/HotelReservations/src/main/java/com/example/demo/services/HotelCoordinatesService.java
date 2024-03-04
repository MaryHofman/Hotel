package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.enteies.HotelCoordinates;

import com.example.demo.reposytories.HotelCoordinatesRepository;

@Component
public class HotelCoordinatesService {
    @Autowired
    private HotelCoordinatesRepository hotelCoordinatesRepository;

    public List<HotelCoordinates> getAllCoordinatesOfHotels(){
        Iterable<HotelCoordinates> CoordinatesOfHotels = hotelCoordinatesRepository.findAll();
        List<HotelCoordinates> ListCoordinatesOfHotels = new ArrayList<>();
        CoordinatesOfHotels.forEach(ListCoordinatesOfHotels::add);
        return ListCoordinatesOfHotels;
    }

    public void save(HotelCoordinates coordinates) {
        hotelCoordinatesRepository.save(coordinates);
    }
}
