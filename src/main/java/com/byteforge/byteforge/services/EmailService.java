package com.byteforge.byteforge.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendVerificationEmail(String to, String token, String appUrl) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("thungar@mail.ru");
        message.setTo(to);
        message.setSubject("Email Verification");
        message.setText("Please click the following link to verify your email: " 
            + appUrl + "/auth/verify?token=" + token);
        mailSender.send(message);
    }
}