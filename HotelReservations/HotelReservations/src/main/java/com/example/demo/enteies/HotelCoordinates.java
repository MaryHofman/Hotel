package com.example.demo.enteies;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "HotelCoordinates")
public class HotelCoordinates {

    @Id
    @Column(name = "hotel_id")
    private Long hotelId;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;   
    
}
