package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.request.ProfileRequestDto;
import com.byteforge.byteforge.dto.response.CustomerNameFromProfileDto;
import com.byteforge.byteforge.dto.response.ProfileResponseDto;
import com.byteforge.byteforge.entities.Customer;
import com.byteforge.byteforge.entities.Profile;
import com.byteforge.byteforge.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private ProfileService profileService;

    private Customer testCustomer;
    private Profile testProfile;
    private ProfileRequestDto testProfileRequest;
    private CustomerNameFromProfileDto testCustomerName;

    @BeforeEach
    void setUp() {
        // Создаем тестового клиента
        testCustomer = new Customer();
        testCustomer.setId(1);
        testCustomer.setEmail("test@example.com");

        // Создаем тестовый профиль
        testProfile = new Profile();
        testProfile.setCustomerId(1);
        testProfile.setFirstName("John");
        testProfile.setLastName("Doe");
        testProfile.setPhone("1234567890");
        testProfile.setCity("Test City");
        testProfile.setAddress("Test Address");
        testProfile.setPostIndex(12345);
        testProfile.setBirthDate(LocalDate.of(1990, 1, 1));
        testProfile.setCustomer(testCustomer);

        // Устанавливаем связь
        testCustomer.setProfile(testProfile);

        // Создаем тестовый запрос на обновление профиля
        testProfileRequest = new ProfileRequestDto(
                "Jane",
                "Smith",
                "9876543210",
                "New City",
                "New Address",
                "54321",
                LocalDate.of(1995, 5, 15)
        );

        // Создаем тестовый DTO имени клиента
        testCustomerName = new CustomerNameFromProfileDto("John");
    }

    @Test
    void getProfileByEmail_ShouldReturnProfileResponseDto() {
        // Arrange
        when(customerRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testCustomer));

        // Act
        ProfileResponseDto result = profileService.getProfileByEmail("test@example.com");

        // Assert
        assertNotNull(result);
        assertEquals(testProfile.getCustomerId(), result.userId());
        assertEquals(testProfile.getFirstName(), result.firstName());
        assertEquals(testProfile.getLastName(), result.lastName());
        assertEquals(testProfile.getPhone(), result.phone());
        assertEquals(testProfile.getCity(), result.city());
        assertEquals(testProfile.getAddress(), result.address());
        assertEquals(testProfile.getPostIndex().toString(), result.postIndex());
        assertEquals(testProfile.getBirthDate(), result.birthDate());
        assertEquals(testProfile.getPhone(), result.phoneNumber());
        assertEquals(testCustomer.getEmail(), result.email());

        verify(customerRepository).findByEmail("test@example.com");
    }

    @Test
    void getProfileByEmail_ShouldThrowExceptionWhenCustomerNotFound() {
        // Arrange
        when(customerRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () ->
                profileService.getProfileByEmail("nonexistent@example.com"));

        assertEquals("Customer not found", exception.getMessage());
        verify(customerRepository).findByEmail("nonexistent@example.com");
    }

    @Test
    void getProfileByEmail_ShouldThrowExceptionWhenProfileNotFound() {
        // Arrange
        Customer customerWithoutProfile = new Customer();
        customerWithoutProfile.setId(2);
        customerWithoutProfile.setEmail("noprofile@example.com");
        customerWithoutProfile.setProfile(null);
        when(customerRepository.findByEmail("noprofile@example.com")).thenReturn(Optional.of(customerWithoutProfile));

        // Act & Assert
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () ->
                profileService.getProfileByEmail("noprofile@example.com"));

        assertEquals("Profile not found", exception.getMessage());
        verify(customerRepository).findByEmail("noprofile@example.com");
    }

    @Test
    void getProfileByEmail_ShouldHandleNullPostIndex() {
        // Arrange
        testProfile.setPostIndex(null);
        when(customerRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testCustomer));

        // Act
        ProfileResponseDto result = profileService.getProfileByEmail("test@example.com");

        // Assert
        assertNotNull(result);
        assertNull(result.postIndex());
        verify(customerRepository).findByEmail("test@example.com");
    }

    @Test
    void getCustomerNameByEmail_ShouldReturnCustomerNameFromProfileDto() {
        // Arrange
        when(customerRepository.findCustomerNameByEmail("test@example.com")).thenReturn(testCustomerName);

        // Act
        CustomerNameFromProfileDto result = profileService.getCustomerNameByEmail("test@example.com");

        // Assert
        assertNotNull(result);
        assertEquals(testCustomerName.firstName(), result.firstName());

        verify(customerRepository).findCustomerNameByEmail("test@example.com");
    }

    @Test
    void updateProfile_ShouldUpdateExistingProfile() {
        // Arrange
        when(customerRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);

        // Act
        profileService.updateProfile("test@example.com", testProfileRequest);

        // Assert
        assertEquals(testProfileRequest.firstName(), testProfile.getFirstName());
        assertEquals(testProfileRequest.lastName(), testProfile.getLastName());
        assertEquals(testProfileRequest.phone(), testProfile.getPhone());
        assertEquals(testProfileRequest.city(), testProfile.getCity());
        assertEquals(testProfileRequest.address(), testProfile.getAddress());
        assertEquals(Integer.valueOf(testProfileRequest.postIndex()), testProfile.getPostIndex());
        assertEquals(testProfileRequest.birthDate(), testProfile.getBirthDate());

        verify(customerRepository).findByEmail("test@example.com");
        verify(customerRepository).save(testCustomer);
    }

    @Test
    void updateProfile_ShouldCreateNewProfileWhenNotExists() {
        // Arrange
        Customer customerWithoutProfile = new Customer();
        customerWithoutProfile.setId(2);
        customerWithoutProfile.setEmail("noprofile@example.com");
        customerWithoutProfile.setProfile(null);
        when(customerRepository.findByEmail("noprofile@example.com")).thenReturn(Optional.of(customerWithoutProfile));
        when(customerRepository.save(any(Customer.class))).thenReturn(customerWithoutProfile);

        // Act
        profileService.updateProfile("noprofile@example.com", testProfileRequest);

        // Assert
        assertNotNull(customerWithoutProfile.getProfile());
        assertEquals(testProfileRequest.firstName(), customerWithoutProfile.getProfile().getFirstName());
        assertEquals(testProfileRequest.lastName(), customerWithoutProfile.getProfile().getLastName());
        assertEquals(testProfileRequest.phone(), customerWithoutProfile.getProfile().getPhone());
        assertEquals(testProfileRequest.city(), customerWithoutProfile.getProfile().getCity());
        assertEquals(testProfileRequest.address(), customerWithoutProfile.getProfile().getAddress());
        assertEquals(Integer.valueOf(testProfileRequest.postIndex()), customerWithoutProfile.getProfile().getPostIndex());
        assertEquals(testProfileRequest.birthDate(), customerWithoutProfile.getProfile().getBirthDate());

        verify(customerRepository).findByEmail("noprofile@example.com");
        verify(customerRepository).save(customerWithoutProfile);
    }

    @Test
    void updateProfile_ShouldThrowExceptionWhenCustomerNotFound() {
        // Arrange
        when(customerRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () ->
                profileService.updateProfile("nonexistent@example.com", testProfileRequest));

        assertEquals("Customer not found", exception.getMessage());
        verify(customerRepository).findByEmail("nonexistent@example.com");
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void updateProfile_ShouldHandleNullPostIndex() {
        // Arrange
        ProfileRequestDto requestWithNullPostIndex = new ProfileRequestDto(
                "Jane",
                "Smith",
                "9876543210",
                "New City",
                "New Address",
                null,
                LocalDate.of(1995, 5, 15)
        );
        when(customerRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);

        // Act
        profileService.updateProfile("test@example.com", requestWithNullPostIndex);

        // Assert
        assertNull(testProfile.getPostIndex());
        verify(customerRepository).findByEmail("test@example.com");
        verify(customerRepository).save(testCustomer);
    }
}
