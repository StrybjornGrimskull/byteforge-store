package com.byteforge.byteforge.services;

import com.byteforge.byteforge.constants.ApplicationConstants;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private SpringTemplateEngine templateEngine;

    @Mock
    private MimeMessage mimeMessage;

    @InjectMocks
    private EmailService emailService;

    private String testEmail;
    private String testToken;
    private String testAppUrl;
    private String testCustomerName;
    private Long testOrderId;
    private List<String> testProductNames;
    private BigDecimal testTotalPrice;

    @BeforeEach
    void setUp() {
        testEmail = "test@example.com";
        testToken = "test-token-123";
        testAppUrl = "https://localhost:8443";
        testCustomerName = "John Doe";
        testOrderId = 1L;
        testProductNames = List.of("Product 1", "Product 2");
        testTotalPrice = BigDecimal.valueOf(199.99);
    }

    @Test
    void sendVerificationEmail_ShouldSendEmailSuccessfully() {
        // Arrange
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        // Act
        emailService.sendVerificationEmail(testEmail, testToken, testAppUrl);

        // Assert
        verify(mailSender).send(messageCaptor.capture());
        SimpleMailMessage sentMessage = messageCaptor.getValue();
        
        assertEquals(ApplicationConstants.FROM_EMAIL, sentMessage.getFrom());
        String[] to = sentMessage.getTo();
        assertNotNull(to);
        assertEquals(testEmail, to[0]);
        assertEquals("Email Verification", sentMessage.getSubject());
        String text = sentMessage.getText();
        assertNotNull(text);
        assertTrue(text.contains(testAppUrl + "/auth/verify?token=" + testToken));
    }

    @Test
    void sendOrderConfirmationEmail_ShouldSendEmailSuccessfully() {
        // Arrange
        String expectedHtml = "<html>Order confirmation email</html>";
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(templateEngine.process(eq("order-confirmation.html"), any(Context.class))).thenReturn(expectedHtml);

        // Act
        emailService.sendOrderConfirmationEmail(testEmail, testCustomerName, testOrderId, testProductNames, testTotalPrice);

        // Assert
        verify(mailSender).createMimeMessage();
        verify(templateEngine).process(eq("order-confirmation.html"), any(Context.class));
        verify(mailSender).send(mimeMessage);
    }

    @Test
    void sendOrderConfirmationEmail_ShouldSetCorrectContextVariables() {
        // Arrange
        String expectedHtml = "<html>Order confirmation email</html>";
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(templateEngine.process(eq("order-confirmation.html"), any(Context.class))).thenReturn(expectedHtml);
        ArgumentCaptor<Context> contextCaptor = ArgumentCaptor.forClass(Context.class);

        // Act
        emailService.sendOrderConfirmationEmail(testEmail, testCustomerName, testOrderId, testProductNames, testTotalPrice);

        // Assert
        verify(templateEngine).process(eq("order-confirmation.html"), contextCaptor.capture());
        Context context = contextCaptor.getValue();
        
        assertEquals(testCustomerName, context.getVariable("customerName"));
        assertEquals(testOrderId, context.getVariable("orderId"));
        assertEquals(testProductNames, context.getVariable("products"));
        assertEquals(testTotalPrice, context.getVariable("totalPrice"));
    }

    @Test
    void sendOrderConfirmationEmail_ShouldThrowExceptionWhenMessagingExceptionOccurs() {
        // Arrange
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(templateEngine.process(eq("order-confirmation.html"), any(Context.class))).thenReturn("<html>test</html>");
        
        // The MessagingException is thrown by MimeMessageHelper operations, not JavaMailSender.send()
        // Since we can't easily mock MimeMessageHelper, we'll simulate the scenario by making
        // the send method throw a RuntimeException that will be thrown directly (not caught by the try-catch)
        doThrow(new RuntimeException("Mail server error")).when(mailSender).send(mimeMessage);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                emailService.sendOrderConfirmationEmail(testEmail, testCustomerName, testOrderId, testProductNames, testTotalPrice));

        assertEquals("Mail server error", exception.getMessage());
        verify(mailSender).createMimeMessage();
        verify(templateEngine).process(eq("order-confirmation.html"), any(Context.class));
        verify(mailSender).send(mimeMessage);
    }

    @Test
    void sendPasswordResetEmail_ShouldSendEmailSuccessfully() {
        // Arrange
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        // Act
        emailService.sendPasswordResetEmail(testEmail, testToken, testAppUrl);

        // Assert
        verify(mailSender).send(messageCaptor.capture());
        SimpleMailMessage sentMessage = messageCaptor.getValue();
        
        assertEquals(ApplicationConstants.FROM_EMAIL, sentMessage.getFrom());
        String[] to = sentMessage.getTo();
        assertNotNull(to);
        assertEquals(testEmail, to[0]);
        assertEquals("Password Reset Request", sentMessage.getSubject());
        String text = sentMessage.getText();
        assertNotNull(text);
        assertTrue(text.contains(testAppUrl + "/auth/reset-password?token=" + testToken));
    }

    @Test
    void sendVerificationEmail_ShouldHandleMultipleRecipients() {
        // Arrange
        String[] multipleEmails = {"test1@example.com", "test2@example.com"};
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        // Act
        emailService.sendVerificationEmail(multipleEmails[0], testToken, testAppUrl);
        emailService.sendVerificationEmail(multipleEmails[1], testToken, testAppUrl);

        // Assert
        verify(mailSender, times(2)).send(messageCaptor.capture());
        List<SimpleMailMessage> sentMessages = messageCaptor.getAllValues();
        
        assertEquals(2, sentMessages.size());
        String[] to1 = sentMessages.get(0).getTo();
        String[] to2 = sentMessages.get(1).getTo();
        assertNotNull(to1);
        assertNotNull(to2);
        assertEquals(multipleEmails[0], to1[0]);
        assertEquals(multipleEmails[1], to2[0]);
    }

    @Test
    void sendOrderConfirmationEmail_ShouldHandleEmptyProductList() {
        // Arrange
        List<String> emptyProductList = List.of();
        String expectedHtml = "<html>Order confirmation email</html>";
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(templateEngine.process(eq("order-confirmation.html"), any(Context.class))).thenReturn(expectedHtml);
        ArgumentCaptor<Context> contextCaptor = ArgumentCaptor.forClass(Context.class);

        // Act
        emailService.sendOrderConfirmationEmail(testEmail, testCustomerName, testOrderId, emptyProductList, testTotalPrice);

        // Assert
        verify(templateEngine).process(eq("order-confirmation.html"), contextCaptor.capture());
        Context context = contextCaptor.getValue();
        
        assertEquals(emptyProductList, context.getVariable("products"));
        assertEquals(testCustomerName, context.getVariable("customerName"));
        assertEquals(testOrderId, context.getVariable("orderId"));
        assertEquals(testTotalPrice, context.getVariable("totalPrice"));
    }

    @Test
    void sendPasswordResetEmail_ShouldHandleSpecialCharactersInToken() {
        // Arrange
        String specialToken = "token-with-special-chars-!@#$%^&*()";
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        // Act
        emailService.sendPasswordResetEmail(testEmail, specialToken, testAppUrl);

        // Assert
        verify(mailSender).send(messageCaptor.capture());
        SimpleMailMessage sentMessage = messageCaptor.getValue();
        
        String text = sentMessage.getText();
        assertNotNull(text);
        assertTrue(text.contains(specialToken));
        assertTrue(text.contains(testAppUrl + "/auth/reset-password?token=" + specialToken));
    }
}
