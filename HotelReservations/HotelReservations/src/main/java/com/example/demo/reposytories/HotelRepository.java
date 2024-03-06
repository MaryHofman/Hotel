package com.example.demo.reposytories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.DTO.HotelCoordinates;
import com.example.demo.enteies.Hotel;

@Repository
public interface HotelRepository extends CrudRepository<Hotel, Long> {
        Optional<Hotel> findById(Long id); 

@Modifying
@Transactional
@Query("UPDATE Hotel h SET h.ratingsCount = :raitingCount, h.totalRating = :totalRaiting WHERE h.hotelId = :id")
int updateHotelRaitingsById(Long id, long raitingCount, long totalRaiting);
        

@Query("SELECT new com.example.demo.DTO.HotelCoordinates(h.hotelId, h.latitude, h.longitude) FROM Hotel h")
List<HotelCoordinates> findLatitudeAndLongitudeById();

}