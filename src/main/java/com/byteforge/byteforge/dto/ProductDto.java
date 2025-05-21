package com.byteforge.byteforge.dto;

import com.byteforge.byteforge.entities.Product;

import java.math.BigDecimal;

public record ProductDto(
        Integer id,
        String name,
        BigDecimal price,
        Integer categoryId,
        String categoryName,
        Integer brandId,
        String brandName,
        Integer warrantyMonths,
        Integer releaseYear,
        String shortDescription,
        String imageUrl,
        Integer stockQuantity
) {
    public static ProductDto fromEntity(Product product, Integer stockQuantity) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getBrand().getId(),
                product.getBrand().getName(),
                product.getWarrantyMonths(),
                product.getReleaseYear(),
                product.getShortDescription(),
                product.getImageUrl(),
                stockQuantity
        );
    }
}