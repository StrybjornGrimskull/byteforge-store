package com.byteforge.byteforge.dto.response;

import java.time.LocalDateTime;

public record ReviewModerationDto(
        Long reviewId,
        String userFirstName,
        Integer rating,
        String text,
        LocalDateTime createdAt,
        String productName
) {
}
