package com.example.demo.reposytories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.enteies.HotelImage;

    @Repository
    public interface HotelImageRepository extends CrudRepository<HotelImage, Long>{
        public List<HotelImage> findAllImagesByHotelId(Long hotelId);
        public void deleteAllImagesByHotelId(Long hotelId);
    }

