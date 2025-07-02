package com.byteforge.byteforge.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;


import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;


    public void sendVerificationEmail(String to, String token, String appUrl) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("thungar@mail.ru");
        message.setTo(to);
        message.setSubject("Email Verification");
        message.setText("Please click the following link to verify your email: " 
            + appUrl + "/auth/verify?token=" + token);
        mailSender.send(message);
    }

    public void sendOrderConfirmationEmail(String to, String customerName, Long orderId, List<String> productNames, double totalPrice) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom("thungar@mail.ru");
            helper.setTo(to);
            helper.setSubject("Order Confirmation");

            // Подготовка Thymeleaf-контекста
            Context context = new Context();
            context.setVariable("customerName", customerName);
            context.setVariable("orderId", orderId);
            context.setVariable("products", productNames);
            context.setVariable("totalPrice", totalPrice);

            // Генерация HTML из шаблона
            String html = templateEngine.process("order-confirmation.html", context);

            helper.setText(html, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send order confirmation email", e);
        }
    }
}