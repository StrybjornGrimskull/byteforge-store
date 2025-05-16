package com.byteforge.byteforge.dto;

import com.byteforge.byteforge.entities.Category;

public record CategoryDto(
        Integer id,
        String name,
        String slug
) {
    public static CategoryDto from(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName(),
                category.getSlug()
        );
    }
}