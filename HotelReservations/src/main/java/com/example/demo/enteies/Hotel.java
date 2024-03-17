package com.example.demo.enteies;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
    private Long hotelId;
    
    @Column(name="name")
    private String name;
    
    @Column(name="address")
    private String address;
    
    @Column(name="price")
    private Double price;
    
    @Column(name="ratings_count")
    private Long ratingsCount;
    
    @Column(name="total_rating")
    private Long totalRating;
    
    @Column(name="latitude")
    private Double latitude;
    
    @Column(name="longitude")
    private Double longitude;
    
    @Column(name="user_id")
    private Long userId;
    
    @Column(name="main_image_url")
    private String imgUrl;
    
    @Column(name="description")
    private String description;

    @OneToMany(mappedBy = "hotelId", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Room> rooms;

    @OneToMany(mappedBy = "hotelId", cascade = CascadeType.REMOVE)
    private List<Extras> extras;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.REMOVE)
    private List<Favourite> favourites;


    @Override
    public String toString() {
        return "Hotel{" +
                "hotelId=" + hotelId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
