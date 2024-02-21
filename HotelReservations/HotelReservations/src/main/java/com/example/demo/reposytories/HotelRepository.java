package com.example.demo.reposytories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.enteies.Hotel;

@Repository
public interface HotelRepository extends CrudRepository<Hotel, Long> {
        Optional<Hotel> findById(Long id); }