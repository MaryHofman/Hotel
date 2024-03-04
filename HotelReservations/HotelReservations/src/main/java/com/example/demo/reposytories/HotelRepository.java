package com.example.demo.reposytories;

import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.enteies.Hotel;

@Repository
public interface HotelRepository extends CrudRepository<Hotel, Long> {
        Optional<Hotel> findById(Long id); 

@Modifying
@Transactional
@Query("UPDATE Hotel h SET h.ratings_count = :raitingCount, h.total_rating = :totalRaiting WHERE h.hotel_id = :id")
int updateHotelRaitingsById(Long id, long raitingCount, long totalRaiting);

}