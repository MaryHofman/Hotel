package com.example.demo.DTO;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class InformationAboutHotel {
    private String name;
    private String description;
    private String address;
    private MultipartFile mainImg;
    private String geography;
    private Double price;
    private List<MultipartFile> allImgs;
    private String[] extras;

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
    public String getGeography() {
        return geography;
    }
    public void setGeography(String geography) {
        this.geography = geography;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
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

    
}
