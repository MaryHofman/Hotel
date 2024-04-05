package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.demo.DTO.HotelCommentDTO;
import com.example.demo.DTO.NewRaiting;
import com.example.demo.DTO.reitingDTO;
import com.example.demo.enteies.HotelComment;
import com.example.demo.services.HotelCommentService;

import java.util.List;

@Controller
public class HotelCommentController {
    @Autowired
    private HotelCommentService hotelCommentService;
    //Добавление комментария
    @PostMapping("/addComments/{hotelId}")
    public ResponseEntity<HotelComment> addHotelComment(@PathVariable Long hotelId, @RequestBody HotelCommentDTO hotelComment, @RequestHeader("Authorization") String jwtToken) {
        hotelComment.setHotelId(hotelId); 
        HotelComment savedComment = hotelCommentService.saveHotelComment(hotelComment, jwtToken);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }
    //Список комментариев к отелю
    @GetMapping("/getCommentsHotels/{hotelId}")
    public ResponseEntity<List<HotelComment>> getAllHotelComments(@PathVariable Long hotelId) {
        List<HotelComment> comments = hotelCommentService.getAllHotelComments(hotelId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
    
    //Удилить комментарий по id
    @DeleteMapping("/deleteComments/{id}")
    public ResponseEntity<Void> deleteHotelCommentById(@PathVariable Long id, @RequestHeader("Authorization") String jwtToken) {
        hotelCommentService.deleteHotelCommentById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //посчитать рейтинг. Возвращает новое значение рейтинга.
    @PostMapping("/calculateRating")
public ResponseEntity<NewRaiting> calculateHotelRating(@RequestBody reitingDTO reiting,@RequestHeader("Authorization") String jwtToken) {
    NewRaiting rating = hotelCommentService.calculateHotelRating(reiting);
    return new ResponseEntity<>(rating, HttpStatus.OK);
}


    

}
