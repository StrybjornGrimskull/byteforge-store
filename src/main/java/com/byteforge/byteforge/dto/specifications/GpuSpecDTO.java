package com.byteforge.byteforge.dto.specifications;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GpuSpecDTO(
        @NotNull(message = "Memory size is required")
        @Min(value = 1, message = "Memory size must be at least 1")
        Integer memorySize,
        
        @NotBlank(message = "Memory type is required")
        String memoryType,
        
        @NotNull(message = "Bus width is required")
        @Min(value = 1, message = "Bus width must be at least 1")
        Integer busWidth,
        
        @NotNull(message = "Base clock is required")
        @Min(value = 1, message = "Base clock must be at least 1")
        Integer baseClock,
        
        @NotNull(message = "Boost clock is required")
        @Min(value = 1, message = "Boost clock must be at least 1")
        Integer boostClock,
        
        @NotNull(message = "TDP is required")
        @Min(value = 1, message = "TDP must be at least 1")
        Integer tdp,
        
        @NotNull(message = "Length is required")
        @Min(value = 1, message = "Length must be at least 1")
        Integer length,
        
        String displayOutputs
) {}
