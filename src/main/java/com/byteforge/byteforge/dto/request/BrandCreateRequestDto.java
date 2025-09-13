package com.byteforge.byteforge.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record BrandCreateRequestDto(
        @NotBlank(message = "Brand name is required")
        @Size(min = 2, max = 100, message = "Brand name must be between 2 and 100 characters")
        String name,
        
        MultipartFile logo
) {}
