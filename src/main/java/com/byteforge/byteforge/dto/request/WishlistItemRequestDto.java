package com.byteforge.byteforge.dto.request;

import com.byteforge.byteforge.entities.WishlistItem;

/**
 * DTO for {@link WishlistItem}
 */
public record WishlistItemRequestDto(
        Integer productId,
        Integer customerId) {}