package com.byteforge.byteforge.dto.specifications;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record SsdSpecDTO(
        @NotNull(message = "Capacity is required")
        @Min(value = 1, message = "Capacity must be at least 1")
        Integer capacity,
        
        @NotBlank(message = "Form factor is required")
        String formFactor,
        
        @NotBlank(message = "Interface type is required")
        String interfaceType,
        
        @NotNull(message = "Read speed is required")
        @Min(value = 1, message = "Read speed must be at least 1")
        Integer readSpeed,
        
        @NotNull(message = "Write speed is required")
        @Min(value = 1, message = "Write speed must be at least 1")
        Integer writeSpeed,
        
        String memoryType,
        
        @NotNull(message = "Endurance TBW is required")
        @Min(value = 1, message = "Endurance TBW must be at least 1")
        Integer enduranceTbw,
        
        Boolean dramCache,
        
        String encryption,
        
        BigDecimal thickness
) {}
