package com.byteforge.byteforge.dto;

import com.byteforge.byteforge.entities.Brand;

public record BrandDto(
        Integer id,
        String name,
        String logoUrl
) {
    public static BrandDto from(Brand brand) {
        return new BrandDto(
                brand.getId(),
                brand.getName(),
                brand.getLogoUrl()
        );
    }
}