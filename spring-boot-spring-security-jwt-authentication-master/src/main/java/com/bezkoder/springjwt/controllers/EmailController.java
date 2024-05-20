package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Service.EmailService;
import com.bezkoder.springjwt.dto.updatePassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class EmailController {
    @Autowired
    EmailService emailService;

    @PostMapping("sendEmail")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody updatePassword updatepassword) {
        return emailService.sendEmail(updatepassword.getEmail());
    }
}
