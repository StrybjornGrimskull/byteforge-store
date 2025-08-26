package com.byteforge.byteforge.dto;

import java.math.BigDecimal;

public record ProductListDto(
        Integer id,
        String name,
        String imageUrl,
        BigDecimal price,
        String brandName,
        Integer categoryId,
        Integer stockQuantity
) {}