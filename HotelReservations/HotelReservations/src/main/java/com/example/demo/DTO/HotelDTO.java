package com.example.demo.DTO;

import java.util.List;

public class HotelDTO {
    
    private Long hotel_id;
    private String name;
    private String address;
    private Long ratings_count;
    private Double total_rating;
    private String geography;
    private Long user_id;
    private String imgUrl;
    public Long getHotel_id() {
        return hotel_id;
    }
    public void setHotel_id(Long hotel_id) {
        this.hotel_id = hotel_id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Long getRatings_count() {
        return ratings_count;
    }
    public void setRatings_count(Long ratings_count) {
        this.ratings_count = ratings_count;
    }
    public Double getTotal_rating() {
        return total_rating;
    }
    public void setTotal_rating(Double total_rating) {
        this.total_rating = total_rating;
    }
    public String getGeography() {
        return geography;
    }
    public void setGeography(String geography) {
        this.geography = geography;
    }
    public Long getUser_id() {
        return user_id;
    }
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    
}
