package com.byteforge.byteforge.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public record ProductUpdateRequestDto(
        @NotBlank(message = "Name must not be empty")
        String name,

        @NotNull(message = "Original price is required")
        BigDecimal originalPrice,

        @Min(value = 0, message = "Discount must be between 0 and 100")
        @Max(value = 100, message = "Discount must be between 0 and 100")
        Integer discountPercentage,

        @NotBlank(message = "Short description must not be empty")
        String shortDescription,

        @NotNull(message = "Stock quantity is required")
        @Min(value = 0, message = "Stock quantity must be 0 or more")
        Integer stockQuantity,

        @NotNull(message = "Category is required")
        Integer categoryId,

        @NotNull(message = "Brand is required")
        Integer brandId,

        @NotNull(message = "Release year is required")
        @Min(value = 1990, message = "Release year must be 1990 or later")
        @Max(value = 2030, message = "Release year must be 2030 or earlier")
        Integer releaseYear,

        @NotNull(message = "Warranty months is required")
        @Min(value = 1, message = "Warranty must be at least 1 month")
        @Max(value = 120, message = "Warranty must be no more than 120 months")
        Integer warrantyMonths,

        MultipartFile productImage
) {}


