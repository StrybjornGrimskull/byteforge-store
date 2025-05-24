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
        LocalDateTime addedDate
) {
    public static WishlistItemResponseDto fromEntity(WishlistItem item) {
        return new WishlistItemResponseDto(
                item.getProduct().getImageUrl(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getAddedDate()
        );
    }
}
