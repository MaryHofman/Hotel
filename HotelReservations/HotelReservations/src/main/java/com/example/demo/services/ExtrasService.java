package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.enteies.Extras;
import com.example.demo.enteies.Hotel;
import com.example.demo.reposytories.ExtrasRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExtrasService {
    @Autowired
    private ExtrasRepository extrasRepository;

    public Extras saveExtras(Extras extras) {
        return extrasRepository.save(extras);
    }

    public Extras getExtrasById(Long id) {
        return extrasRepository.findById(id).orElse(null);
    }

    public List<String> getAllByHotelId(Hotel hotelId) {
        Extras extras = extrasRepository.findByHotelId(hotelId);
        List<String> allStrings = new ArrayList<>();
        allStrings.addAll(extras.getStringArray());
        
        return allStrings;
    }
    

    @Transactional
    public void deleteByHotelId(Hotel hotelId){
        extrasRepository.deleteByHotelId(hotelId);
    }

}
