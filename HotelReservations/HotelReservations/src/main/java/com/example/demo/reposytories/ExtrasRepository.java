package com.example.demo.reposytories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.enteies.Extras;
import com.example.demo.enteies.Hotel;

@Repository
public interface ExtrasRepository extends JpaRepository<Extras, Long> {
    String[] findAllByHotelId(Hotel hotel);
    void deleteByHotelId(Hotel hotel);
}
