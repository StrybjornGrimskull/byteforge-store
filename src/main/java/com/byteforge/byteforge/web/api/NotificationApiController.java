package com.byteforge.byteforge.web.api;

import com.byteforge.byteforge.dto.response.NotificationDto;
import com.byteforge.byteforge.entities.Notification;
import com.byteforge.byteforge.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationApiController {
    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<Notification> createNotification(
            @RequestParam Integer userId,
            @RequestParam String message) {
        Notification notification = notificationService.createNotification(userId, message);
        return ResponseEntity.status(HttpStatus.CREATED).body(notification);
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long notificationId) {
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDto>> getAllNotificationsByUserId(@PathVariable Integer userId) {
        List<NotificationDto> notifications = notificationService.getAllNotificationsByUserId(userId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<List<NotificationDto>> getUnreadNotificationsByUserId(@PathVariable Integer userId) {
        List<NotificationDto> notifications = notificationService.getUnreadNotificationsByUserId(userId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/user/{userId}/unread/count")
    public ResponseEntity<Long> getUnreadNotificationsCount(@PathVariable Integer userId) {
        long count = notificationService.getUnreadNotificationsCount(userId);
        return ResponseEntity.ok(count);
    }

    @PutMapping("/{notificationId}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/user/{userId}/read-all")
    public ResponseEntity<Void> markAllAsRead(@PathVariable Integer userId) {
        notificationService.markAllAsRead(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
