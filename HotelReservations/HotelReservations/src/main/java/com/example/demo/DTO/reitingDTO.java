package com.example.demo.DTO;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class reitingDTO {
    private Long hotelId;
    private Long userId;
    private int raiting;
    
    public Long getHotelId() {
        return hotelId;
    }
    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public int getRaiting() {
        return raiting;
    }
    public void setRaiting(int raiting) {
        this.raiting = raiting;
    }

    

}
