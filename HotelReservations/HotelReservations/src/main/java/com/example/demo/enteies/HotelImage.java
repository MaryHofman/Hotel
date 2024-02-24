package com.example.demo.enteies;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "hotel_images")
public class HotelImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hotel_id")
    private Long hotelId;

    @Column(name = "image_url")
    private String imageUrl;

    
}