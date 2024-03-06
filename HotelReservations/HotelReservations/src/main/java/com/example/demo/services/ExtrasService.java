package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.enteies.Extras;
import com.example.demo.reposytories.ExtrasRepository;

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

    public List<Extras> getAllExtras() {
        return extrasRepository.findAll();
    }

    public String[] getAllByHotelId(Long hotelId){
            return extrasRepository.findAllByHotelId(hotelId);
    }

}
