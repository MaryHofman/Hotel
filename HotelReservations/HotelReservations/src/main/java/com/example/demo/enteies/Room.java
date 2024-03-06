package com.example.demo.enteies;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "rooms")
@Data
public class Room {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "room_number", nullable = false)
    private int roomNumber;

    @Column(name = "hotel_id", nullable = false)
    private int hotelId;

    @Column(name = "room_type", nullable = false, length = 50)
    private String roomType;

    @Column(name = "price", nullable = false, columnDefinition = "DOUBLE PRECISION")
    private Double price;

    @Column(name = "is_occupied", nullable = false)
    private boolean isOccupied;
}

