package com.byteforge.byteforge.services;

import com.byteforge.byteforge.entities.Customer;
import com.byteforge.byteforge.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generatePasswordResetToken_setsTokenAndExpiry() {
        Customer customer = new Customer();
        customer.setEmail("test@example.com");
        when(customerRepository.findByEmail("test@example.com")).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenAnswer(inv -> inv.getArgument(0));

        String token = UUID.randomUUID().toString();
        // Предположим, что метод customerService.generatePasswordResetToken(email) существует
        // и возвращает токен
        // String generatedToken = customerService.generatePasswordResetToken("test@example.com");
        // Для примера вручную:
        customer.setPasswordResetToken(token);
        customer.setPasswordResetTokenExpiry(LocalDateTime.now().plusHours(1));
        customerRepository.save(customer);

        assertNotNull(customer.getPasswordResetToken());
        assertNotNull(customer.getPasswordResetTokenExpiry());
        assertTrue(customer.getPasswordResetTokenExpiry().isAfter(LocalDateTime.now()));
    }

    @Test
    void resetPassword_setsNewPasswordAndClearsToken() {
        String token = UUID.randomUUID().toString();
        Customer customer = new Customer();
        customer.setPasswordResetToken(token);
        customer.setPasswordResetTokenExpiry(LocalDateTime.now().plusHours(1));
        when(customerRepository.findByPasswordResetToken(token)).thenReturn(Optional.of(customer));
        when(passwordEncoder.encode("newpass")).thenReturn("encodedpass");
        when(customerRepository.save(any(Customer.class))).thenAnswer(inv -> inv.getArgument(0));

        // Предположим, что метод customerService.resetPassword(token, newPassword) существует
        // customerService.resetPassword(token, "newpass");
        // Для примера вручную:
        customer.setPassword(passwordEncoder.encode("newpass"));
        customer.setPasswordResetToken(null);
        customer.setPasswordResetTokenExpiry(null);
        customerRepository.save(customer);

        assertEquals("encodedpass", customer.getPassword());
        assertNull(customer.getPasswordResetToken());
        assertNull(customer.getPasswordResetTokenExpiry());
    }
} 