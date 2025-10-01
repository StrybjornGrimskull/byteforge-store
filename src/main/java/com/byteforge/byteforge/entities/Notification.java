package com.byteforge.byteforge.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notifications")
@FieldDefaults(level = PRIVATE)
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    Customer user;

    @Column(nullable = false, columnDefinition = "TEXT")
    String message;

    @Column(name = "is_read", nullable = false)
    boolean isRead = false;

    @Column(name = "created_at")
    LocalDateTime createdAt = LocalDateTime.now();
}
