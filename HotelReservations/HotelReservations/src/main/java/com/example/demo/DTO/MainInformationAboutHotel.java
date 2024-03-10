package com.example.demo.DTO;

import java.util.List;

import com.example.demo.enteies.Room;

public class MainInformationAboutHotel {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String mainImgURL;
    private List<String> allImgsURL;
    private List<String> extras;
    private Coordinates geo;
    private List<Room> rooms;
    
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
    public String getMainImgURL() {
        return mainImgURL;
    }
    public void setMainImgURL(String mainImgURL) {
        this.mainImgURL = mainImgURL;
    }
    public List<String> getAllImgsURL() {
        return allImgsURL;
    }
    public void setAllImgsURL(List<String> allImgsURL) {
        this.allImgsURL = allImgsURL;
    }
    
    public Coordinates getGeo() {
        return geo;
    }
    public void setGeo(Coordinates geo) {
        this.geo = geo;
    }
    public List<Room> getRooms() {
        return rooms;
    }
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
    public List<String> getExtras() {
        return extras;
    }
    public void setExtras(List<String> extras) {
        this.extras = extras;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    
}
