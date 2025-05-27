package com.byteforge.byteforge.dto.response;

import com.byteforge.byteforge.entities.ShoppingCart;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ShoppingCartResponseDto (
    String imageUrl,
    Integer productId,
    String productName,
    BigDecimal price,
    Integer quantity,
    LocalDateTime addedDate
) {
        public static ShoppingCartResponseDto fromEntity(ShoppingCart item) {
            return new ShoppingCartResponseDto(
                    item.getProduct().getImageUrl(),
                    item.getProduct().getId(),
                    item.getProduct().getName(),
                    item.getProduct().getPrice(),
                    item.getProduct().getStockQuantity().getQuantity(),
                    item.getAddedDate()
            );
        }
    }
