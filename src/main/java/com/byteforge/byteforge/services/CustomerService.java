package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.request.ConsumerRequestDto;
import com.byteforge.byteforge.entities.Authority;
import com.byteforge.byteforge.entities.Customer;
import com.byteforge.byteforge.repositories.AuthorityRepository;
import com.byteforge.byteforge.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final EmailService emailService;
    private final String appUrl = "http://localhost:8080";

    @Transactional
    public void registerNewUser(ConsumerRequestDto registrationDto) {
//        validateRegistration(registrationDto);

        Customer customer = new Customer();
        customer.setEmail(registrationDto.email());
        customer.setPassword(passwordEncoder.encode(registrationDto.password()));
        customer.setEmailVerified(false);
        customer.setEmailVerificationToken(UUID.randomUUID().toString());

        Customer savedCustomer = customerRepository.save(customer);

        Authority authority = new Authority("ROLE_USER", savedCustomer);
        authorityRepository.save(authority);

        savedCustomer.setAuthorities(Set.of(authority));

        // Отправка email с подтверждением
        emailService.sendVerificationEmail(
                savedCustomer.getEmail(),
                savedCustomer.getEmailVerificationToken(),
                appUrl
        );
    }

    public void verifyEmail(String token) {
        Customer customer = customerRepository.findByEmailVerificationToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid verification token"));

        customer.setEmailVerified(true);
        customer.setEmailVerificationToken(null);
        customerRepository.save(customer);
    }

    private void validateRegistration(ConsumerRequestDto registrationDto) {
        // Проверка совпадения паролей
        if (!registrationDto.password().equals(registrationDto.confirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        // Проверка существования email
        if (customerRepository.findByEmail(registrationDto.email()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
    }
}