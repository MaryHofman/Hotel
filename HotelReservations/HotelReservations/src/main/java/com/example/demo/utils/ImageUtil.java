package com.example.demo.utils;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Component
public class ImageUtil {
   

    public  String saveImage(MultipartFile file, String path) throws IOException {
        if (file != null && !file.isEmpty()) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            fileName = System.currentTimeMillis() + "-" + fileName; 
            File uploadDir = new File(path);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();}

            Path filePath = Paths.get(path + File.separator + fileName);
            Files.copy(file.getInputStream(), filePath);
            return fileName; 
        } else {
            throw new IllegalArgumentException("Uploaded file is empty or null.");
        }
    }


    public  void deleteImage(String fileName, String path) throws IOException {
        Path imagePath = Paths.get(path + File.separator + fileName);
        Files.deleteIfExists(imagePath);
    }


    public String saveMainImage(String mainImg) {

        String base64ImageString = mainImg.split(",")[1];
        byte[] imageBytes = Base64.getDecoder().decode(base64ImageString);
        String uniqueFileName = UUID.randomUUID().toString() + ".jpg";
        MultipartFile file = new MockMultipartFile(uniqueFileName, imageBytes);
        String uploadPath ="img/mainIMG/";
        Path path = Paths.get(uploadPath);
        try {
            Files.createDirectories(path);
            System.out.println("Папка создана или уже существует: " + path);
        } catch (IOException e) {
            System.err.println("Не удалось создать папку: " + e.getMessage());
        } 
        String filePath = uploadPath + uniqueFileName;
        System.out.println(filePath);
        try {
            Files.write(Paths.get(filePath), file.getBytes());
            System.out.println("Файл успешно записан на диск: " + filePath);
            
        } catch (IOException e) {
            System.err.println("Не удалось записать файл на диск: " + e.getMessage());
        }
        return filePath;
        
    }


    public String saveAllImage(String File) {

        String base64ImageString = File.split(",")[1];
        byte[] imageBytes = Base64.getDecoder().decode(base64ImageString);
        String uniqueFileName = UUID.randomUUID().toString() + ".jpg";
        MultipartFile file = new MockMultipartFile(uniqueFileName, imageBytes);
        String uploadPath ="img/hotelIMG/";
        Path path = Paths.get(uploadPath);
        try {
            Files.createDirectories(path);
            System.out.println("Папка создана или уже существует: " + path);
        } catch (IOException e) {
            System.err.println("Не удалось создать папку: " + e.getMessage());
        } 
        String filePath = uploadPath + uniqueFileName;
        System.out.println(filePath);
        try {
            Files.write(Paths.get(filePath), file.getBytes());
            System.out.println("Файл успешно записан на диск: " + filePath);
            
        } catch (IOException e) {
            System.err.println("Не удалось записать файл на диск: " + e.getMessage());
        }
        return filePath;
    }


    public String saveUserImage(String profilePhoto) {
        String base64ImageString = profilePhoto.split(",")[1];
        byte[] imageBytes = Base64.getDecoder().decode(base64ImageString);
        String uniqueFileName = UUID.randomUUID().toString() + ".jpg";
        MultipartFile file = new MockMultipartFile(uniqueFileName, imageBytes);
        String uploadPath ="img/users/";
        Path path = Paths.get(uploadPath);
        try {
            Files.createDirectories(path);
            System.out.println("Папка создана или уже существует: " + path);
        } catch (IOException e) {
            System.err.println("Не удалось создать папку: " + e.getMessage());
        } 
        String filePath = uploadPath + uniqueFileName;
        System.out.println(filePath);
        try {
            Files.write(Paths.get(filePath), file.getBytes());
            System.out.println("Файл успешно записан на диск: " + filePath);
            
        } catch (IOException e) {
            System.err.println("Не удалось записать файл на диск: " + e.getMessage());
        }
        return filePath;
    }


}
