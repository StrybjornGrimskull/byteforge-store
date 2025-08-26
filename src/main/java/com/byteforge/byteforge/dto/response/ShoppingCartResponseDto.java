package com.byteforge.byteforge.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ShoppingCartResponseDto (
        String imageUrl,
        Integer productId,
        String productName,
        BigDecimal price,
        Integer quantity, // Количество в корзине
        Integer stockQuantity, // Доступное количество на складе
        LocalDateTime addedDate
) {}