package com.bezkoder.springjwt.Service;

import org.springframework.http.ResponseEntity;

public interface EmailService {
    ResponseEntity<?> sendEmail(String email);
}
