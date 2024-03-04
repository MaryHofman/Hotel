package com.example.demo.DTO;

import org.springframework.format.annotation.DateTimeFormat;

public class HotelCard {
   
    private Long Id;
    private String name;
    private String address;
    private Double rating;
    private Double price;
    private String geography;
    private String imgUrl;
    
    
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
  
    public String getGeography() {
        return geography;
    }
    public void setGeography(String geography) {
        this.geography = geography;
    }
    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public Double getRating() {
        return rating;
    }
    public void setRating(Double rating) {
        this.rating = rating;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Long getId() {
        return Id;
    }
    public void setId(Long id) {
        Id = id;
    }
    
    
}
