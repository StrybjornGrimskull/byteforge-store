package com.byteforge.byteforge.dto.response;

import java.math.BigDecimal;

public record ProductResponseDto(
        Integer id,
        String name,
        BigDecimal price,
        Integer categoryId,
        String categoryName,
        String brandName,
        String brandLogo,
        Integer warrantyMonths,
        Integer releaseYear,
        String shortDescription,
        String imageUrl,
        Integer stockQuantity
) {}
