package com.example.demo.DTO;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class reitingDTO {
    private Long hotelId;
    private String userName;
    private int raiting;

    
    
    public reitingDTO() {
    }

    
    public Long getHotelId() {
        return hotelId;
    }
    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }
    
    public int getRaiting() {
        return raiting;
    }
    public void setRaiting(int raiting) {
        this.raiting = raiting;
    }
    public String getUserName() {
        return userName;
    }

    

}
