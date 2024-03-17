package com.example.demo.DTO;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.enteies.Room;

public class InformationAboutHotel {
    private Long id;
    private String name;
    private String description;
    private String address;
    private List<String> allImgs;
    public List<String> getAllImgs() {
        return allImgs;
    }
    public void setAllImgs(List<String> allImgs) {
        this.allImgs = allImgs;
    }
    private String mainImg;
    public String getMainImg() {
        return mainImg;
    }
    public void setMainImg(String mainImg) {
        this.mainImg = mainImg;
    }
    private String[] extras;
    private Coordinates geo;
    private List<RoomDTO> rooms;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
  
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String[] getExtras() {
        return extras;
    }
    public void setExtras(String[] extras) {
        this.extras = extras;
    }
    public Coordinates getGeo() {
        return geo;
    }
    public void setGeo(Coordinates geo) {
        this.geo = geo;
    }
    public List<RoomDTO> getRooms() {
        return rooms;
    }
    public void setRooms(List<RoomDTO> rooms) {
        this.rooms = rooms;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    
}
