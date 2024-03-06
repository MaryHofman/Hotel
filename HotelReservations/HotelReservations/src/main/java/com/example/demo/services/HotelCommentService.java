package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.HotelCommentDTO;
import com.example.demo.DTO.NewRaiting;
import com.example.demo.DTO.reitingDTO;
import com.example.demo.enteies.Hotel;
import com.example.demo.enteies.HotelComment;
import com.example.demo.reposytories.HotelCommentRepository;
import com.example.demo.reposytories.HotelRepository;

import java.util.List;

@Service
public class HotelCommentService {

    @Autowired
    private HotelCommentRepository hotelCommentRepository;  
    @Autowired
    private HotelService hotelService;

    public HotelComment saveHotelComment(HotelCommentDTO hotelCommentDTO) {

        HotelComment hotelComment=new HotelComment();
        hotelComment.setHotelId(hotelCommentDTO.getHotelId());
        hotelComment.setUserId(hotelCommentDTO.getUserId());
        hotelComment.setCommentText(hotelCommentDTO.getCommentText());
        hotelComment.setCreatedAt(hotelCommentDTO.getCreatedAt());

        return hotelCommentRepository.save(hotelComment);
    }

    public List<HotelComment> getAllHotelComments(Long idHotel) {
        return hotelCommentRepository.findAllByHotelId(idHotel);
    }

    
    public void deleteHotelCommentById(Long id) {
        hotelCommentRepository.deleteById(id);
    }

    public NewRaiting calculateHotelRating(reitingDTO reiting) {
        Hotel hotel=hotelService.findHotelById(reiting.getHotelId());
        float newRaiting=hotelService.changeInformationAboutRaiting(reiting.getHotelId(), hotel.getRatingsCount()+1, reiting.getRaiting()+hotel.getTotalRating());
        NewRaiting raiting=new NewRaiting();
        raiting.setHotelId(hotel.getHotelId());
        raiting.setNewRaiting(newRaiting);
        return raiting;
    }
}
