package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Service.EmailService;
import com.bezkoder.springjwt.Service.Impl.UserDetailsServiceImpl;
import com.bezkoder.springjwt.dto.ResponseJson;
import com.bezkoder.springjwt.dto.updatePassword;
import com.bezkoder.springjwt.dto.validateOTP;
import com.bezkoder.springjwt.entities.User;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class EmailController {
    @Autowired
    EmailService emailService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("sendEmail")
    public ResponseEntity<?> sendOTP(@Valid @RequestBody updatePassword updatepassword) {
        UserDetails user = userDetailsService.loadUserByUsername(updatepassword.getEmail());

        if(ObjectUtils.isEmpty(user)) {
            return ResponseEntity.badRequest().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.NOT_FOUND, "Email Not Found"));
        }
        boolean check = emailService.sendEmail(updatepassword);
        if(!check){
            return ResponseEntity.badRequest().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.NOT_FOUND, "Email Not Found"));
        }
        return ResponseEntity.ok().body(new ResponseJson<>(Boolean.TRUE, HttpStatus.OK, "Sent an Email for user"));
    }

    @PostMapping("validateOTP")
    public ResponseEntity<?> validateOTP(@Valid @RequestBody validateOTP OTP){
        boolean check = emailService.validateOTP(OTP);
        if(!check){
            return ResponseEntity.badRequest().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.NOT_FOUND, "OTP Not Found"));
        }
        return ResponseEntity.ok().body(new ResponseJson<>(Boolean.TRUE, HttpStatus.OK, "Success"));
    }
}
