package com.example.demo.reposytories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.enteies.Favourite;
import com.example.demo.enteies.Hotel;
import com.example.demo.enteies.Users;

import jakarta.transaction.Transactional;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
   
        @Modifying
        @Transactional
        @Query("DELETE FROM Favourite f WHERE f.user = :user AND f.hotel = :hotel")
        void deleteByUserAndHotel(@Param("user") Users user, @Param("hotel") Hotel hotel);

        List<Favourite> findByUser(Users user);
    }
    
