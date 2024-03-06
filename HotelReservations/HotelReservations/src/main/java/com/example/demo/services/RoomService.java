package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.enteies.Hotel;
import com.example.demo.enteies.Room;
import com.example.demo.reposytories.RoomRepository;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public String saveRoom(Room room){
            Room savedRoom = roomRepository.save(room);
            if (savedRoom.getId() != null) {
                return ("Комната успешно сохранена. Идентификатор комнаты: " + savedRoom.getId());
            } else {
                return ("Ошибка при сохранении комнаты.");
            }
        
    }

    public List<Room> findAllByHotelId(Hotel hotelId){
        List<Room> rooms=roomRepository.findAllByHotelId(hotelId);
        return rooms;
    }

    public String deleteRoom(Long roomId){
        Optional<Room> roomOptional = roomRepository.findById(roomId);
    if (roomOptional.isPresent()) {
        roomRepository.deleteById(roomId);
        return "Комната с идентификатором " + roomId + " успешно удалена.";
    } else {
        return "Комната с идентификатором " + roomId + " не найдена.";
    }
    }

    




    
}
