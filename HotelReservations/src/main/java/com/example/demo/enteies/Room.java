package com.example.demo.enteies;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "room")
@Data
public class Room {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hotel_id", referencedColumnName = "hotel_id")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    @JsonIgnore
    private Hotel hotelId;

    @Column(name = "room_type", nullable = false, length = 50)
    private String roomType;

    @Column(name = "price", nullable = false, columnDefinition = "DOUBLE PRECISION")
    private Double price;

    @Column(name = "description")
    private String description;

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomType='" + roomType + '\'' +
                ", price=" + price +
                '}';
    }
}

