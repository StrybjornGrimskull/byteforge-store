package com.byteforge.byteforge.dto.specifications;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record MonitorSpecDTO(
        @NotNull(message = "Screen size is required")
        @DecimalMin(value = "0.1", message = "Screen size must be at least 0.1")
        BigDecimal screenSize,
        
        @NotBlank(message = "Resolution is required")
        String resolution,
        
        @NotBlank(message = "Panel type is required")
        String panelType,
        
        @NotNull(message = "Refresh rate is required")
        @Min(value = 1, message = "Refresh rate must be at least 1")
        Integer refreshRate,
        
        @NotNull(message = "Response time is required")
        @Min(value = 1, message = "Response time must be at least 1")
        Integer responseTime
) {}
