package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.request.ConsumerRequestDto;
import com.byteforge.byteforge.dto.request.UserRoleUpdateRequest;
import com.byteforge.byteforge.entities.Authority;
import com.byteforge.byteforge.entities.Customer;
import com.byteforge.byteforge.entities.Profile;
import com.byteforge.byteforge.exceptions.EmailAlreadyExistsException;
import com.byteforge.byteforge.exceptions.PasswordMismatchException;
import com.byteforge.byteforge.repositories.AuthorityRepository;
import com.byteforge.byteforge.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.authentication.password.CompromisedPasswordDecision;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthorityRepository authorityRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private CompromisedPasswordChecker compromisedPasswordChecker;

    @InjectMocks
    private CustomerService customerService;

    private ConsumerRequestDto validRegistrationDto;
    private Customer testCustomer;
    private Authority testAuthority;

    @BeforeEach
    void setUp() {
        validRegistrationDto = new ConsumerRequestDto(
                "John",
                "Doe",
                "test@example.com",
                "password123",
                "password123"
        );

        testCustomer = new Customer();
        testCustomer.setId(1);
        testCustomer.setEmail("test@example.com");
        testCustomer.setPassword("encodedPassword");
        testCustomer.setEmailVerified(false);
        testCustomer.setEmailVerificationToken("test-token");

        Profile profile = new Profile();
        profile.setFirstName("John");
        profile.setLastName("Doe");
        profile.setCustomer(testCustomer);
        testCustomer.setProfile(profile);

        testAuthority = new Authority("ROLE_USER", testCustomer);
        testCustomer.setAuthorities(Set.of(testAuthority));
    }

    @Test
    void registerNewUser_ShouldCreateUserSuccessfully() {
        // Arrange
        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);
        when(authorityRepository.save(any(Authority.class))).thenReturn(testAuthority);
        // Mock the compromised password checker to return a non-compromised result
        when(compromisedPasswordChecker.check(anyString())).thenAnswer(invocation -> {
            // Create a mock CompromisedPasswordDecision that returns false for isCompromised()
            CompromisedPasswordDecision mockDecision = mock(CompromisedPasswordDecision.class);
            when(mockDecision.isCompromised()).thenReturn(false);
            return mockDecision;
        });

        // Act
        customerService.registerNewUser(validRegistrationDto);

        // Assert
        verify(customerRepository).findByEmail(validRegistrationDto.email());
        verify(passwordEncoder).encode(validRegistrationDto.password());
        verify(customerRepository).save(any(Customer.class));
        verify(authorityRepository).save(any(Authority.class));
        verify(emailService).sendVerificationEmail(anyString(), anyString(), anyString());
        verify(compromisedPasswordChecker).check(validRegistrationDto.password());
    }

    @Test
    void registerNewUser_ShouldThrowExceptionWhenEmailAlreadyExists() {
        // Arrange
        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.of(testCustomer));

        // Act & Assert
        assertThrows(EmailAlreadyExistsException.class, () -> 
                customerService.registerNewUser(validRegistrationDto));
        
        verify(customerRepository).findByEmail(validRegistrationDto.email());
        verify(customerRepository, never()).save(any(Customer.class));
        verify(compromisedPasswordChecker, never()).check(anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(authorityRepository, never()).save(any(Authority.class));
        verify(emailService, never()).sendVerificationEmail(anyString(), anyString(), anyString());
    }

    @Test
    void registerNewUser_ShouldThrowExceptionWhenPasswordsDoNotMatch() {
        // Arrange
        ConsumerRequestDto invalidDto = new ConsumerRequestDto(
                "John",
                "Doe",
                "test@example.com",
                "password123",
                "differentPassword"
        );

        // Act & Assert
        assertThrows(PasswordMismatchException.class, () -> 
                customerService.registerNewUser(invalidDto));
        
        verify(customerRepository, never()).findByEmail(anyString());
        verify(customerRepository, never()).save(any(Customer.class));
        verify(compromisedPasswordChecker, never()).check(anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(authorityRepository, never()).save(any(Authority.class));
        verify(emailService, never()).sendVerificationEmail(anyString(), anyString(), anyString());
    }

    @Test
    void verifyEmail_ShouldVerifyEmailSuccessfully() {
        // Arrange
        when(customerRepository.findByEmailVerificationToken(anyString())).thenReturn(Optional.of(testCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);

        // Act
        Customer result = customerService.verifyEmail("test-token");

        // Assert
        assertTrue(result.isEmailVerified());
        assertNull(result.getEmailVerificationToken());
        verify(customerRepository).findByEmailVerificationToken("test-token");
        verify(customerRepository).save(testCustomer);
    }

    @Test
    void verifyEmail_ShouldReturnCustomerWhenAlreadyVerified() {
        // Arrange
        testCustomer.setEmailVerified(true);
        when(customerRepository.findByEmailVerificationToken(anyString())).thenReturn(Optional.of(testCustomer));

        // Act
        Customer result = customerService.verifyEmail("test-token");

        // Assert
        assertTrue(result.isEmailVerified());
        verify(customerRepository).findByEmailVerificationToken("test-token");
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void verifyEmail_ShouldThrowExceptionWhenTokenNotFound() {
        // Arrange
        when(customerRepository.findByEmailVerificationToken(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> 
                customerService.verifyEmail("invalid-token"));
        
        verify(customerRepository).findByEmailVerificationToken("invalid-token");
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void resendVerificationEmail_ShouldResendEmailSuccessfully() {
        // Arrange
        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.of(testCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);

        // Act
        customerService.resendVerificationEmail("test@example.com");

        // Assert
        verify(customerRepository).findByEmail("test@example.com");
        verify(customerRepository).save(any(Customer.class));
        verify(emailService).sendVerificationEmail(anyString(), anyString(), anyString());
    }

    @Test
    void resendVerificationEmail_ShouldThrowExceptionWhenEmailNotFound() {
        // Arrange
        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> 
                customerService.resendVerificationEmail("nonexistent@example.com"));
        
        verify(customerRepository).findByEmail("nonexistent@example.com");
        verify(customerRepository, never()).save(any(Customer.class));
        verify(emailService, never()).sendVerificationEmail(anyString(), anyString(), anyString());
    }

    @Test
    void resendVerificationEmail_ShouldThrowExceptionWhenEmailAlreadyVerified() {
        // Arrange
        testCustomer.setEmailVerified(true);
        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.of(testCustomer));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> 
                customerService.resendVerificationEmail("test@example.com"));
        
        verify(customerRepository).findByEmail("test@example.com");
        verify(customerRepository, never()).save(any(Customer.class));
        verify(emailService, never()).sendVerificationEmail(anyString(), anyString(), anyString());
    }

    @Test
    void generatePasswordResetToken_ShouldGenerateTokenSuccessfully() {
        // Arrange
        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.of(testCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);

        // Act
        customerService.generatePasswordResetToken("test@example.com");

        // Assert
        verify(customerRepository).findByEmail("test@example.com");
        verify(customerRepository).save(any(Customer.class));
        verify(emailService).sendPasswordResetEmail(anyString(), anyString(), anyString());
    }

    @Test
    void resetPassword_ShouldThrowExceptionWhenTokenExpired() {
        // Arrange
        testCustomer.setPasswordResetToken("reset-token");
        testCustomer.setPasswordResetTokenExpiry(LocalDateTime.now().minusHours(1));
        
        when(customerRepository.findByPasswordResetToken(anyString())).thenReturn(Optional.of(testCustomer));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> 
                customerService.resetPassword("reset-token", "newPassword123"));
        
        verify(customerRepository).findByPasswordResetToken("reset-token");
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void emailExists_ShouldReturnTrueWhenEmailExists() {
        // Arrange
        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.of(testCustomer));

        // Act
        boolean result = customerService.emailExists("test@example.com");

        // Assert
        assertTrue(result);
        verify(customerRepository).findByEmail("test@example.com");
    }

    @Test
    void emailExists_ShouldReturnFalseWhenEmailDoesNotExist() {
        // Arrange
        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Act
        boolean result = customerService.emailExists("nonexistent@example.com");

        // Assert
        assertFalse(result);
        verify(customerRepository).findByEmail("nonexistent@example.com");
    }

    @Test
    void getAllCustomersForRoleManagement_ShouldReturnPageOfCustomers() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<Customer> customerPage = new PageImpl<>(List.of(testCustomer), pageable, 1);
        
        when(customerRepository.findAllCustomersWithAuthoritiesAndFilters(anyString(), anyString(), any(Pageable.class)))
                .thenReturn(customerPage);

        // Act
        Page<com.byteforge.byteforge.dto.response.CustomerRoleManagementDto> result = 
                customerService.getAllCustomersForRoleManagement(pageable, "test", "ROLE_USER");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(customerRepository).findAllCustomersWithAuthoritiesAndFilters("test", "ROLE_USER", pageable);
    }

    @Test
    void updateUserRoles_ShouldUpdateRolesSuccessfully() {
        // Arrange
        UserRoleUpdateRequest request = new UserRoleUpdateRequest(1, List.of("ROLE_ADMIN", "ROLE_USER"));
        when(customerRepository.findById(anyInt())).thenReturn(Optional.of(testCustomer));
        when(authorityRepository.save(any(Authority.class))).thenReturn(testAuthority);

        // Act
        customerService.updateUserRoles(request);

        // Assert
        verify(customerRepository).findById(1);
        verify(authorityRepository).deleteByCustomerId(1);
        verify(authorityRepository, times(2)).save(any(Authority.class));
    }

    @Test
    void updateUserRoles_ShouldThrowExceptionWhenUserNotFound() {
        // Arrange
        UserRoleUpdateRequest request = new UserRoleUpdateRequest(999, List.of("ROLE_ADMIN"));
        when(customerRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> 
                customerService.updateUserRoles(request));
        
        verify(customerRepository).findById(999);
        verify(authorityRepository, never()).deleteByCustomerId(anyInt());
        verify(authorityRepository, never()).save(any(Authority.class));
    }
}