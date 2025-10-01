package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.dto.response.NotificationDto;
import com.byteforge.byteforge.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT new com.byteforge.byteforge.dto.response.NotificationDto(" +
           "n.id, n.message, n.isRead, n.createdAt) " +
           "FROM Notification n " +
           "WHERE n.user.id = :userId " +
           "ORDER BY n.createdAt DESC")
    List<NotificationDto> findNotificationDtosByUserId(@Param("userId") Integer userId);

    @Query("SELECT new com.byteforge.byteforge.dto.response.NotificationDto(" +
           "n.id, n.message, n.isRead, n.createdAt) " +
           "FROM Notification n " +
           "WHERE n.user.id = :userId AND n.isRead = false " +
           "ORDER BY n.createdAt DESC")
    List<NotificationDto> findUnreadNotificationDtosByUserId(@Param("userId") Integer userId);

    @Query("SELECT COUNT(n) FROM Notification n WHERE n.user.id = :userId AND n.isRead = false")
    long countUnreadByUserId(@Param("userId") Integer userId);
}
