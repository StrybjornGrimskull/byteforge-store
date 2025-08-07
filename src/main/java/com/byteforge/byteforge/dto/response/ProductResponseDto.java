package com.byteforge.byteforge.dto.response;

import com.byteforge.byteforge.entities.Product;

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
) {
    public static ProductResponseDto fromEntity(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getBrand().getName(),
                product.getBrand().getLogoUrl(),
                product.getWarrantyMonths(),
                product.getReleaseYear(),
                product.getShortDescription(),
                product.getImageUrl(),
                product.getStockQuantity().getQuantity()
        );
    }

}
