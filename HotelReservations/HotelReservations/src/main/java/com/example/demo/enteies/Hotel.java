package com.example.demo.enteies;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@Table(name="hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="hotel_id")
    private Long hotel_id;
    @Column(name="name")
    private String name;
    @Column(name="address")
    private String address;
    @Column(name="price")
    private Integer price;
    @Column(name="ratings_count")
    private Long ratings_count;
    @Column(name="total_rating")
    private Double total_rating;
    @Column(name="geography")
    private String geography;
    @Column(name="user_id")
    private Long user_id;
    @Column(name="main_image_url")
    private String imgUrl;

    
}
