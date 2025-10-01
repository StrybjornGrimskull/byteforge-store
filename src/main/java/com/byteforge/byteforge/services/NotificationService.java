package com.byteforge.byteforge.services;

import com.byteforge.byteforge.constants.ApplicationConstants;
import com.byteforge.byteforge.dto.response.NotificationDto;
import com.byteforge.byteforge.entities.Customer;
import com.byteforge.byteforge.entities.Notification;
import com.byteforge.byteforge.repositories.CustomerRepository;
import com.byteforge.byteforge.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public Notification createNotification(Integer userId, String message) {
        Customer customer = customerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(ApplicationConstants.CUSTOMER_NOT_FOUND));

        Notification notification = new Notification();
        notification.setUser(customer);
        notification.setMessage(message);
        notification.setRead(false);

        return notificationRepository.save(notification);
    }

    @Transactional
    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    @Transactional(readOnly = true)
    public List<NotificationDto> getAllNotificationsByUserId(Integer userId) {
        return notificationRepository.findNotificationDtosByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<NotificationDto> getUnreadNotificationsByUserId(Integer userId) {
        return notificationRepository.findUnreadNotificationDtosByUserId(userId);
    }

    @Transactional(readOnly = true)
    public long getUnreadNotificationsCount(Integer userId) {
        return notificationRepository.countUnreadByUserId(userId);
    }

    @Transactional
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    @Transactional
    public void markAllAsRead(Integer userId) {
        List<Notification> unreadNotifications = notificationRepository.findAll()
                .stream()
                .filter(n -> n.getUser().getId().equals(userId) && !n.isRead())
                .toList();
        
        unreadNotifications.forEach(notification -> notification.setRead(true));
        notificationRepository.saveAll(unreadNotifications);
    }
}
