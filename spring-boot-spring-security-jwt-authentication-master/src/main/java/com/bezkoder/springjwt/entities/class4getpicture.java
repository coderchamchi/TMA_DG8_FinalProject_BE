package com.bezkoder.springjwt.entities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class class4getpicture {
    public String getbase64fromfolder(String productname) throws IOException {
        String imagePath = "D:\\INTERN_TMA_DG8\\TMA_DG8_FinalProject\\imgProject\\"+productname+".jpeg";
        byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        return base64Image;
    }
}
