package com.example.demo.controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.HotelDTO;
import com.example.demo.DTO.IdWrapper;
import com.example.demo.DTO.InformationAboutHotel;
import com.example.demo.enteies.Hotel;
import com.example.demo.services.HotelService;
import org.springframework.util.Base64Utils;


@Controller
public class CreateController {
    @Autowired
    private HotelService hotelService;
    
    @PostMapping("/create")
    public ResponseEntity<?> createHotel(@RequestBody InformationAboutHotel informationAboutHotel, @RequestHeader("Authorization") String jwtToken) throws IOException {
        String base64ImageString = informationAboutHotel.getMainImg().split(",")[1];
        byte[] imageBytes = Base64.getDecoder().decode(base64ImageString);
        MultipartFile file = new MockMultipartFile("image.jpg", imageBytes);
        String uploadPath ="src/img/hotelIMG/";
    
        Path path = Paths.get(uploadPath);
        try {
            Files.createDirectories(path);
            System.out.println("Папка создана или уже существует: " + path);
        } catch (IOException e) {
            System.err.println("Не удалось создать папку: " + e.getMessage());
        }
        
        // Определите полный путь к файлу на диске
        String filePath = uploadPath + "image.jpg";
        System.out.println(filePath);
        
        // Создайте файл на диске и запишите в него данные из MultipartFile
        try {
            Files.write(Paths.get(filePath), file.getBytes());
            System.out.println("Файл успешно записан на диск: " + filePath);
            
        } catch (IOException e) {
            System.err.println("Не удалось записать файл на диск: " + e.getMessage());
        }
        
        return hotelService.createCard(informationAboutHotel, jwtToken);
    }
    

    @DeleteMapping("/delete")
public ResponseEntity<?> deleteHotel(@RequestBody IdWrapper idWrapper, @RequestHeader("Authorization") String jwtToken) throws IOException {
    return hotelService.deleteInformationAboutHotel(idWrapper.getId_article(), jwtToken);
}

    
}
