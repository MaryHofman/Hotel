package com.example.demo.utils;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
}
