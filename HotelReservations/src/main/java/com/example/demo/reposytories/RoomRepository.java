package com.example.demo.reposytories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.enteies.Hotel;
import com.example.demo.enteies.Room;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {
    void deleteByHotelId(Hotel hotelId);
    List<Room> findAllByHotelId(Hotel hotelId);
}
