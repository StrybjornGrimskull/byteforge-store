package com.byteforge.byteforge.dto;

import com.byteforge.byteforge.dto.specifications.ProductSpecDTO;

import java.math.BigDecimal;

public record ProductCreateRequestDTO(
        String name,
        BigDecimal price,
        Integer categoryId,
        Integer brandId,
        Integer warrantyMonths,
        Integer releaseYear,
        String shortDescription,
        String imageUrl,
        Integer stockQuantity,
        ProductSpecDTO spec
) {}
