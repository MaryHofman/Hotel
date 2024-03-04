package com.example.demo.reposytories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.enteies.HotelCoordinates;

@Repository
public interface HotelCoordinatesRepository extends CrudRepository<HotelCoordinates, Long>{
    
}