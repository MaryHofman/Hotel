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

    @PostMapping
    public ResponseEntity<HotelComment> addHotelComment(@RequestBody HotelCommentDTO hotelComment) {
        HotelComment savedComment = hotelCommentService.saveHotelComment(hotelComment);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<HotelComment>> getAllHotelComments(Long hotelId) {
        List<HotelComment> comments = hotelCommentService.getAllHotelComments(hotelId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotelCommentById(@PathVariable Long id) {
        hotelCommentService.deleteHotelCommentById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @PostMapping("/calculateRating")
public ResponseEntity<NewRaiting> calculateHotelRating(@RequestBody reitingDTO reiting) {
    NewRaiting rating = hotelCommentService.calculateHotelRating(reiting);
    return new ResponseEntity<>(rating, HttpStatus.OK);
}


    

}
