package com.bezkoder.springjwt.Service.Impl;

import com.bezkoder.springjwt.Service.EmailService;
import com.bezkoder.springjwt.dto.ResponseJson;
import com.bezkoder.springjwt.dto.updatePassword;
import com.bezkoder.springjwt.dto.validateOTP;
import com.bezkoder.springjwt.entities.User;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;


@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public boolean sendEmail(updatePassword updatepassword) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(updatepassword.getEmail());
        Random random = new Random();
        int min = 1000;
        int max = 9999;
        String OTP = String.valueOf((random.nextInt(max - min + 1) + min));
        msg.setSubject("NHẬP MÃ OTP NÀY ĐỂ XÁC NHẬN CÂP NHẬT LẠI MẬT KHẨU NHÉ");
        msg.setText("Mã OTP: " + OTP);
        UserDetails user = userDetailsService.loadUserByUsername(updatepassword.getEmail());
        if(ObjectUtils.isEmpty(user)) {
            return false;
        }
        Optional<User> userOutput = userRepository.findByEmail(updatepassword.getEmail());
        if(userOutput.isPresent()){
            User userGet = userOutput.get();
            userGet.setOTP(OTP);
            userRepository.save(userGet);
        }
        javaMailSender.send(msg);
        return true;
    }

    public boolean validateOTP(validateOTP OTP) {

        UserDetails user = userDetailsService.loadUserByUsername(OTP.getEmail());
        if(ObjectUtils.isEmpty(user)) {
            return false;
        }
        Optional<User> userOutput = userRepository.findByEmail(OTP.getEmail());
        if(userOutput.isPresent()){
            User userValid = userOutput.get();
            return Objects.equals(userValid.getOTP(), OTP.getOtp());
        }
        return false;
    }

    public void sendEmailWithAttachment() throws MessagingException, IOException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo("1@gmail.com");
        helper.setSubject("Testing from Spring Boot");
        helper.setText("<h1>Check attachment for image!</h1>", true);
        helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));
        javaMailSender.send(msg);
    }
}
