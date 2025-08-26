package com.byteforge.byteforge.dto.response;

import com.byteforge.byteforge.entities.WishlistItem;

import java.time.LocalDateTime;

/**
 * DTO for {@link WishlistItem}
 */
public record WishlistItemResponseDto(
        String imageUrl,
        Integer productId,
        String productName,
        Integer quantity,
        LocalDateTime addedDate
) {}
