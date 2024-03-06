package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.DTO.HotelCommentDTO;
import com.example.demo.DTO.NewRaiting;
import com.example.demo.DTO.reitingDTO;
import com.example.demo.enteies.HotelComment;
import com.example.demo.services.HotelCommentService;

import java.util.List;

@RestController
@RequestMapping("/hotelComments")
public class HotelCommentController {

    @Autowired
    private HotelCommentService hotelCommentService;
    //Добавление комментария
    @PostMapping("/add/{hotelId}")
    public ResponseEntity<HotelComment> addHotelComment(@PathVariable Long hotelId, @RequestBody HotelCommentDTO hotelComment) {
        hotelComment.setHotelId(hotelId); 
        HotelComment savedComment = hotelCommentService.saveHotelComment(hotelComment);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }
    //Список комментариев к отелю
    @GetMapping("/getHotels/{hotelId}")
    public ResponseEntity<List<HotelComment>> getAllHotelComments(@PathVariable Long hotelId) {
        List<HotelComment> comments = hotelCommentService.getAllHotelComments(hotelId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
    
    //Удилить комментарий по id
    @DeleteMapping("/deleteComments/{id}")
    public ResponseEntity<Void> deleteHotelCommentById(@PathVariable Long id) {
        hotelCommentService.deleteHotelCommentById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //посчитать рейтинг. Возвращает новое значение рейтинга.
    @PostMapping("/calculateRating")
public ResponseEntity<NewRaiting> calculateHotelRating(@RequestBody reitingDTO reiting) {
    NewRaiting rating = hotelCommentService.calculateHotelRating(reiting);
    return new ResponseEntity<>(rating, HttpStatus.OK);
}


    

}
