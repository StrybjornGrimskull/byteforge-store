package com.byteforge.byteforge.dto.specifications;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CpuSpecDTO(
        @NotNull(message = "Cores is required")
        @Min(value = 1, message = "Cores must be at least 1")
        Integer cores,
        
        @NotNull(message = "Threads is required")
        @Min(value = 1, message = "Threads must be at least 1")
        Integer threads,
        
        @NotNull(message = "Base clock is required")
        @DecimalMin(value = "0.1", message = "Base clock must be at least 0.1")
        BigDecimal baseClock,
        
        @NotNull(message = "Boost clock is required")
        @DecimalMin(value = "0.1", message = "Boost clock must be at least 0.1")
        BigDecimal boostClock,
        
        @NotBlank(message = "Socket is required")
        String socket,
        
        @NotNull(message = "Cache size is required")
        @Min(value = 1, message = "Cache size must be at least 1")
        Integer cacheSize,
        
        @NotNull(message = "TDP is required")
        @Min(value = 1, message = "TDP must be at least 1")
        Integer tdp,
        
        Boolean integratedGpu
) {}
