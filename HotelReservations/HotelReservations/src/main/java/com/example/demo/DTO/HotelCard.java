package com.example.demo.DTO;

import org.springframework.format.annotation.DateTimeFormat;

public class HotelCard {
   
    private String name;
    private String address;
    private Double rating;
    private Integer price;
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
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    
    
}
