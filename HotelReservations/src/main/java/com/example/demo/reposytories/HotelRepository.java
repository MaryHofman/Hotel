package com.example.demo.reposytories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.DTO.HotelCoordinates;
import com.example.demo.enteies.Hotel;

@Repository
public interface HotelRepository extends CrudRepository<Hotel, Long> {

    Optional<Hotel> findById(Long id); 

    @Modifying
    @Transactional
    @Query("UPDATE Hotel h SET h.ratingsCount = :ratingCount, h.totalRating = :totalRating WHERE h.id = :id")
    int updateHotelRatingsById(@Param("id") Long id, @Param("ratingCount") long ratingCount, @Param("totalRating") long totalRating);

    @Query("SELECT new com.example.demo.DTO.HotelCoordinates(h.hotelId, h.latitude, h.longitude) FROM Hotel h")
    List<HotelCoordinates> findLatitudeAndLongitudeById();

    @Transactional
    @Override
    void deleteById(Long id);

    @Query("SELECT h FROM Hotel h WHERE h.userId = :userId")
    List<Hotel> findHotelsByUserId(@Param("userId") Long userId);
}

