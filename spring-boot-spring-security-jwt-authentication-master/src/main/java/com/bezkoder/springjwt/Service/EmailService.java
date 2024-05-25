package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.dto.updatePassword;
import com.bezkoder.springjwt.dto.validateOTP;
import org.springframework.http.ResponseEntity;

public interface EmailService {
    boolean sendEmail(updatePassword updatepassword);

    boolean validateOTP(validateOTP otp);
}
