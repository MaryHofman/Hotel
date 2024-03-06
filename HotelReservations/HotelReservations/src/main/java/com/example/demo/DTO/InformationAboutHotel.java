package com.example.demo.DTO;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.enteies.Room;

public class InformationAboutHotel {
    private String name;
    private String description;
    private String address;
    private MultipartFile mainImg;
    private List<MultipartFile> allImgs;
    private String[] extras;
    private GeoIP geo;
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
    public MultipartFile getMainImg() {
        return mainImg;
    }
    public void setMainImg(MultipartFile mainImg) {
        this.mainImg = mainImg;
    }
    
    public List<MultipartFile> getAllImgs() {
        return allImgs;
    }
    public void setAllImgs(List<MultipartFile> allImgs) {
        this.allImgs = allImgs;
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
    public GeoIP getGeo() {
        return geo;
    }
    public void setGeo(GeoIP geo) {
        this.geo = geo;
    }
    public List<RoomDTO> getRooms() {
        return rooms;
    }
    public void setRooms(List<RoomDTO> rooms) {
        this.rooms = rooms;
    }

    
}
