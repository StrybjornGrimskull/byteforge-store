package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.request.ConsumerRequestDto;
import com.byteforge.byteforge.dto.request.UserRoleUpdateRequest;
import com.byteforge.byteforge.dto.response.CustomerRoleManagementDto;
import com.byteforge.byteforge.entities.Authority;
import com.byteforge.byteforge.entities.Customer;
import com.byteforge.byteforge.entities.Profile;
import com.byteforge.byteforge.repositories.AuthorityRepository;
import com.byteforge.byteforge.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

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

        // Проверяем, не верифицирован ли уже email
        if (customer.isEmailVerified()) {
            return customer; // Возвращаем пользователя без изменений
        }
        
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

    @Transactional
    public void generatePasswordResetToken(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User with this email not found"));

        String token = UUID.randomUUID().toString();
        customer.setPasswordResetToken(token);
        customer.setPasswordResetTokenExpiry(LocalDateTime.now().plusHours(1)); // Token valid for 1 hour
        customerRepository.save(customer);

        emailService.sendPasswordResetEmail(customer.getEmail(), token, appUrl);
    }

    @Transactional
    public void resetPassword(String token, String newPassword) {
        Customer customer = customerRepository.findByPasswordResetToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid password reset token"));

        if (customer.getPasswordResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Password reset token has expired");
        }

        customer.setPassword(passwordEncoder.encode(newPassword));
        customer.setPasswordResetToken(null);
        customer.setPasswordResetTokenExpiry(null);
        customerRepository.save(customer);
    }

    public boolean emailExists(String email) {
        return customerRepository.findByEmail(email).isPresent();
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

    public Page<CustomerRoleManagementDto> getAllCustomersForRoleManagement(Pageable pageable, String search, String role) {
        // Подготавливаем параметры для поиска
        String searchParam = (search != null && !search.trim().isEmpty()) ? search.trim() : null;
        String roleParam = (role != null && !role.trim().isEmpty()) ? role.trim() : null;
        
        // Используем один универсальный метод
        Page<Customer> customers = customerRepository.findAllCustomersWithAuthoritiesAndFilters(searchParam, roleParam, pageable);
        
        return customers.map(customer -> {
            Collection<String> authorities = customer.getAuthorities().stream()
                    .map(Authority::getName)
                    .toList();
            return new CustomerRoleManagementDto(customer.getId(), customer.getEmail(), authorities);
        });
    }

    @Transactional
    public void updateUserRoles(UserRoleUpdateRequest request) {
        Customer customer = customerRepository.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Удаляем все существующие роли
        authorityRepository.deleteByCustomerId(customer.getId());

        // Добавляем новые роли
        for (String roleName : request.roles()) {
            Authority authority = new Authority(roleName, customer);
            authorityRepository.save(authority);
        }
    }
}