package com.byteforge.byteforge.dto.specifications;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CaseSpecDTO(
        @NotBlank(message = "Form factor is required")
        String formFactor,
        
        @NotBlank(message = "Motherboard support is required")
        String motherboardSupport,
        
        @NotNull(message = "Max GPU length is required")
        @Min(value = 1, message = "Max GPU length must be at least 1")
        Integer maxGpuLength,
        
        @NotNull(message = "Max CPU cooler height is required")
        @Min(value = 1, message = "Max CPU cooler height must be at least 1")
        Integer maxCpuCoolerHeight,
        
        @NotNull(message = "Fans included is required")
        @Min(value = 0, message = "Fans included must be at least 0")
        Integer fansIncluded,
        
        String radiatorSupport
) {}
