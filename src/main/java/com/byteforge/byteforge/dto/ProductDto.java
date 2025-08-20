package com.byteforge.byteforge.dto;

import com.byteforge.byteforge.entities.Product;

import java.math.BigDecimal;

public record ProductDto(

        String name,
        BigDecimal price,
        String imageUrl
) {
    public static ProductDto fromEntity(Product product) {
        return new ProductDto(
                product.getName(),
                product.getPrice(),
                product.getImageUrl()
        );
    }
}