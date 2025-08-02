package com.byteforge.byteforge.dto;

import com.byteforge.byteforge.entities.Product;

import java.math.BigDecimal;

public record ProductListDto(
        Integer id,
        String name,
        String imageUrl,
        BigDecimal price,
        String brandName,
        Integer stockQuantity
) {
    public static ProductListDto fromEntity(Product product) {
        return new ProductListDto(
                product.getId(),
                product.getName(),
                product.getImageUrl(),
                product.getPrice(),
                product.getBrand().getName(),
                product.getStockQuantity() != null ? product.getStockQuantity().getQuantity() : 0
        );
    }
} 