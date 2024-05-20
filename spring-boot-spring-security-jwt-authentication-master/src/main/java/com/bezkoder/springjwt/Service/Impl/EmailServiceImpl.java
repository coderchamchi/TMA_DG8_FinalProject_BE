package com.bezkoder.springjwt.Service.Impl;

import com.bezkoder.springjwt.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Random;


@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public ResponseEntity<?> sendEmail(String email) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        Random random = new Random();
        int min = 1000;
        int max = 9999;
        String OTP = String.valueOf((random.nextInt(max - min + 1) + min));
        msg.setSubject("NHẬP MÃ OTP NÀY ĐỂ XÁC NHẬN CÂP NHẬT LẠI MẬT KHẨU NHÉ");
        msg.setText(OTP);
        javaMailSender.send(msg);
        return null;
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
