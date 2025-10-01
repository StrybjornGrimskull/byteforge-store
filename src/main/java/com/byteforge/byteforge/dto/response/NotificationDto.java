package com.byteforge.byteforge.dto.response;

import java.time.LocalDateTime;

public record NotificationDto(
        Long id,
        String message,
        boolean isRead,
        LocalDateTime createdAt
) {
}
