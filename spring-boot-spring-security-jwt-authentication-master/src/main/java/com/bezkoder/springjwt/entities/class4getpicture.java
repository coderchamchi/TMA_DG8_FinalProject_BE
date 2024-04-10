package com.bezkoder.springjwt.entities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class class4getpicture {
    public String getbase64fromfolder(String productName) throws IOException {
        String imagePath = "D:\\image100ngayha\\PRODUCT\\" + productName + ".png";
        try{
            byte[] imageBytes = Files.readAllBytes(Path.of(imagePath));
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            // Log the exception with more details (e.getMessage())
            throw new IOException("Failed to read image file: " + imagePath, e);
        }
    }
}
