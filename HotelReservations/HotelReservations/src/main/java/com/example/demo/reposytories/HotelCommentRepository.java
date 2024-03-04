package com.example.demo.reposytories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.enteies.HotelComment;

@Repository
public interface HotelCommentRepository extends JpaRepository<HotelComment, Long> {
   public List<HotelComment> findAllByHotelId(Long hotelId);
}
