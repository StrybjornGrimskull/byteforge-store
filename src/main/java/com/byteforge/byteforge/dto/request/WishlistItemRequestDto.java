package com.byteforge.byteforge.dto.request;

import com.byteforge.byteforge.dto.ProductResponseDto;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.entities.WishlistItem;

import java.time.LocalDateTime;

/**
 * DTO for {@link WishlistItem}
 */
public record WishlistItemRequestDto(
        Integer productId,
        Integer customerId) {
    public static WishlistItemRequestDto fromEntity(WishlistItem wishlistItem) {

        return new WishlistItemRequestDto(
                wishlistItem.getProduct().getId(),
                wishlistItem.getCustomer().getId()
        );
    }
}