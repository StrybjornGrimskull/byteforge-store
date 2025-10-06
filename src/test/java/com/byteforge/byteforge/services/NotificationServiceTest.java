package com.byteforge.byteforge.services;

import com.byteforge.byteforge.constants.ApplicationConstants;
import com.byteforge.byteforge.dto.response.NotificationDto;
import com.byteforge.byteforge.entities.Customer;
import com.byteforge.byteforge.entities.Notification;
import com.byteforge.byteforge.repositories.CustomerRepository;
import com.byteforge.byteforge.repositories.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private NotificationService notificationService;

    private Customer testCustomer;
    private Notification testNotification;
    private NotificationDto testNotificationDto;
    private Integer testUserId;
    private Long testNotificationId;
    private String testMessage;

    @BeforeEach
    void setUp() {
        testUserId = 1;
        testNotificationId = 1L;
        testMessage = "Test notification message";
        LocalDateTime testCreatedAt = LocalDateTime.now();

        testCustomer = new Customer();
        testCustomer.setId(testUserId);
        testCustomer.setEmail("test@example.com");

        testNotification = new Notification();
        testNotification.setId(testNotificationId);
        testNotification.setUser(testCustomer);
        testNotification.setMessage(testMessage);
        testNotification.setRead(false);

        testNotificationDto = new NotificationDto(
                testNotificationId,
                testMessage,
                false,
                testCreatedAt
        );
    }

    @Test
    void createNotification_ShouldCreateAndReturnNotification_WhenCustomerExists() {
        // Arrange
        when(customerRepository.findById(testUserId)).thenReturn(Optional.of(testCustomer));
        when(notificationRepository.save(any(Notification.class))).thenReturn(testNotification);

        // Act
        Notification result = notificationService.createNotification(testUserId, testMessage);

        // Assert
        assertNotNull(result);
        assertEquals(testMessage, result.getMessage());
        assertEquals(testCustomer, result.getUser());
        assertFalse(result.isRead());
        verify(customerRepository).findById(testUserId);
        verify(notificationRepository).save(any(Notification.class));
    }

    @Test
    void createNotification_ShouldThrowRuntimeException_WhenCustomerNotFound() {
        // Arrange
        when(customerRepository.findById(testUserId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                notificationService.createNotification(testUserId, testMessage));

        assertEquals(ApplicationConstants.CUSTOMER_NOT_FOUND, exception.getMessage());
        verify(customerRepository).findById(testUserId);
        verify(notificationRepository, never()).save(any());
    }

    @Test
    void deleteNotification_ShouldDeleteNotification() {
        // Act
        notificationService.deleteNotification(testNotificationId);

        // Assert
        verify(notificationRepository).deleteById(testNotificationId);
    }

    @Test
    void getAllNotificationsByUserId_ShouldReturnNotificationDtos() {
        // Arrange
        List<NotificationDto> expectedDtos = List.of(testNotificationDto);
        when(notificationRepository.findNotificationDtosByUserId(testUserId)).thenReturn(expectedDtos);

        // Act
        List<NotificationDto> result = notificationService.getAllNotificationsByUserId(testUserId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testNotificationDto, result.getFirst());
        verify(notificationRepository).findNotificationDtosByUserId(testUserId);
    }

    @Test
    void getUnreadNotificationsByUserId_ShouldReturnUnreadNotificationDtos() {
        // Arrange
        List<NotificationDto> expectedDtos = List.of(testNotificationDto);
        when(notificationRepository.findUnreadNotificationDtosByUserId(testUserId)).thenReturn(expectedDtos);

        // Act
        List<NotificationDto> result = notificationService.getUnreadNotificationsByUserId(testUserId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testNotificationDto, result.getFirst());
        verify(notificationRepository).findUnreadNotificationDtosByUserId(testUserId);
    }

    @Test
    void getUnreadNotificationsCount_ShouldReturnCount() {
        // Arrange
        long expectedCount = 5L;
        when(notificationRepository.countUnreadByUserId(testUserId)).thenReturn(expectedCount);

        // Act
        long result = notificationService.getUnreadNotificationsCount(testUserId);

        // Assert
        assertEquals(expectedCount, result);
        verify(notificationRepository).countUnreadByUserId(testUserId);
    }

    @Test
    void markAsRead_ShouldMarkNotificationAsRead_WhenNotificationExists() {
        // Arrange
        when(notificationRepository.findById(testNotificationId)).thenReturn(Optional.of(testNotification));
        when(notificationRepository.save(any(Notification.class))).thenReturn(testNotification);

        // Act
        notificationService.markAsRead(testNotificationId);

        // Assert
        assertTrue(testNotification.isRead());
        verify(notificationRepository).findById(testNotificationId);
        verify(notificationRepository).save(testNotification);
    }

    @Test
    void markAsRead_ShouldThrowRuntimeException_WhenNotificationNotFound() {
        // Arrange
        when(notificationRepository.findById(testNotificationId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                notificationService.markAsRead(testNotificationId));

        assertEquals("Notification not found", exception.getMessage());
        verify(notificationRepository).findById(testNotificationId);
        verify(notificationRepository, never()).save(any());
    }

    @Test
    void markAllAsRead_ShouldMarkAllUnreadNotificationsAsRead() {
        // Arrange
        Notification unreadNotification1 = new Notification();
        unreadNotification1.setId(1L);
        unreadNotification1.setUser(testCustomer);
        unreadNotification1.setRead(false);

        Notification unreadNotification2 = new Notification();
        unreadNotification2.setId(2L);
        unreadNotification2.setUser(testCustomer);
        unreadNotification2.setRead(false);

        List<Notification> allNotifications = List.of(unreadNotification1, unreadNotification2);
        when(notificationRepository.findAll()).thenReturn(allNotifications);
        when(notificationRepository.saveAll(any())).thenReturn(allNotifications);

        // Act
        notificationService.markAllAsRead(testUserId);

        // Assert
        assertTrue(unreadNotification1.isRead());
        assertTrue(unreadNotification2.isRead());
        verify(notificationRepository).findAll();
        verify(notificationRepository).saveAll(any());
    }

    @Test
    void markAllAsRead_ShouldNotMarkNotificationsFromOtherUsers() {
        // Arrange
        Customer otherCustomer = new Customer();
        otherCustomer.setId(2);

        Notification userNotification = new Notification();
        userNotification.setId(1L);
        userNotification.setUser(testCustomer);
        userNotification.setRead(false);

        Notification otherUserNotification = new Notification();
        otherUserNotification.setId(2L);
        otherUserNotification.setUser(otherCustomer);
        otherUserNotification.setRead(false);

        List<Notification> allNotifications = List.of(userNotification, otherUserNotification);
        when(notificationRepository.findAll()).thenReturn(allNotifications);
        when(notificationRepository.saveAll(any())).thenReturn(allNotifications);

        // Act
        notificationService.markAllAsRead(testUserId);

        // Assert
        assertTrue(userNotification.isRead());
        assertFalse(otherUserNotification.isRead()); // Should remain unread
        verify(notificationRepository).findAll();
        verify(notificationRepository).saveAll(any());
    }

    @Test
    void markAllAsRead_ShouldNotMarkAlreadyReadNotifications() {
        // Arrange
        Notification readNotification = new Notification();
        readNotification.setId(1L);
        readNotification.setUser(testCustomer);
        readNotification.setRead(true);

        List<Notification> allNotifications = List.of(readNotification);
        when(notificationRepository.findAll()).thenReturn(allNotifications);
        when(notificationRepository.saveAll(any())).thenReturn(allNotifications);

        // Act
        notificationService.markAllAsRead(testUserId);

        // Assert
        assertTrue(readNotification.isRead()); // Should remain read
        verify(notificationRepository).findAll();
        verify(notificationRepository).saveAll(any());
    }
}
