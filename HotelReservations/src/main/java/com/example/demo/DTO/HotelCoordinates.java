package com.example.demo.DTO;

public class HotelCoordinates {
   
    private Long hotelId;
    private Double latitude;
    private Double longitude;

    
    public HotelCoordinates(Long hotelId, Double latitude, Double longitude) {
        this.hotelId = hotelId;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    public Long getHotelId() {
        return hotelId;
    }
    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }
    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    } 

    

}
