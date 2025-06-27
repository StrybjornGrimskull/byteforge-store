package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.request.ConsumerRequestDto;
import com.byteforge.byteforge.entities.Authority;
import com.byteforge.byteforge.entities.Customer;
import com.byteforge.byteforge.entities.Profile;
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
    private final String appUrl = "https://localhost:8443";

     @Transactional
    public void registerNewUser(ConsumerRequestDto registrationDto) {
        validateRegistration(registrationDto);

        Customer customer = new Customer();
        customer.setEmail(registrationDto.email());
        customer.setPassword(passwordEncoder.encode(registrationDto.password()));
        customer.setEmailVerified(false);
        customer.setEmailVerificationToken(UUID.randomUUID().toString());

        // Создаем профиль пользователя
         Profile profile = new Profile();
         profile.setFirstName(registrationDto.firstName());
         profile.setLastName(registrationDto.lastName());
         profile.setCustomer(customer);
         customer.setProfile(profile);

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

    @Transactional
    public Customer verifyEmail(String token) {
        Customer customer = customerRepository.findByEmailVerificationToken(token)
                .orElseThrow(() -> new RuntimeException("Неверный токен подтверждения"));

        customer.setEmailVerified(true);
        customer.setEmailVerificationToken(null); // Токен обнуляется
        return customerRepository.save(customer); // Возвращаем обновлённого пользователя
    }

    public void resendVerificationEmail(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email не найден"));

        if (customer.isEmailVerified()) {
            throw new RuntimeException("Email уже подтвержден");
        }

        String newToken = UUID.randomUUID().toString();
        customer.setEmailVerificationToken(newToken);
        customerRepository.save(customer);

        emailService.sendVerificationEmail(email, newToken, appUrl);
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