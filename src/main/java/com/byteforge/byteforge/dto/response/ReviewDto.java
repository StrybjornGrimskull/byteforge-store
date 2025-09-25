package com.byteforge.byteforge.dto.response;

import java.time.LocalDateTime;

public record ReviewDto(
        Long id,
        String userFirstName,
        Integer rating,
        String text,
        LocalDateTime createdAt,
        boolean active
) {
}
