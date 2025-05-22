package com.byteforge.byteforge.dto.response;

import com.byteforge.byteforge.dto.ProductDto;
import com.byteforge.byteforge.dto.ProductResponseDto;
import com.byteforge.byteforge.entities.Customer;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.entities.WishlistItem;

import java.time.LocalDateTime;

/**
 * DTO for {@link WishlistItem}
 */
public record WishlistItemDto(
        Integer id,
        String imageUrl,
        String productName,
        LocalDateTime addedDate
) {
    public static WishlistItemDto fromEntity(WishlistItem item) {
        return new WishlistItemDto(
                item.getId(),
                item.getProduct().getImageUrl(),
                item.getProduct().getName(),
                item.getAddedDate()
        );
    }
}
