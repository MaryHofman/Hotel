package com.example.demo.DTO;

import java.time.LocalDateTime;

public class HotelCommentDTO{
    private Long hotelId;
    private String userName;
    private String commentText;
    private LocalDateTime createdAt;
    private int raiting;
    
    public Long getHotelId() {
        return hotelId;
    }
    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }
    
    
    public String getCommentText() {
        return commentText;
    }
    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public int getRaiting() {
        return raiting;
    }
    public void setRaiting(int raiting) {
        this.raiting = raiting;
    }

    
}
