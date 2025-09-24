package com.byteforge.byteforge.dto.specifications;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RamSpecDTO(
        @NotNull(message = "Memory size is required")
        @Min(value = 1, message = "Memory size must be at least 1")
        Integer memorySize,
        
        @NotNull(message = "Modules count is required")
        @Min(value = 1, message = "Modules count must be at least 1")
        Integer modulesCount,
        
        @NotNull(message = "Speed is required")
        @Min(value = 1, message = "Speed must be at least 1")
        Integer speed,
        
        @NotBlank(message = "Type is required")
        String type,
        
        @NotBlank(message = "Timings is required")
        String timings,
        
        @NotNull(message = "Voltage is required")
        @DecimalMin(value = "0.1", message = "Voltage must be at least 0.1")
        BigDecimal voltage
) {}
