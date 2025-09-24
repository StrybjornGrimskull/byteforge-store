package com.byteforge.byteforge.dto.specifications;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MotherboardSpecDTO(
        @NotBlank(message = "Socket is required")
        String socket,
        
        @NotBlank(message = "Chipset is required")
        String chipset,
        
        @NotBlank(message = "Form factor is required")
        String formFactor,
        
        @NotNull(message = "Memory slots is required")
        @Min(value = 1, message = "Memory slots must be at least 1")
        Integer memorySlots,
        
        @NotNull(message = "Max memory is required")
        @Min(value = 1, message = "Max memory must be at least 1")
        Integer maxMemory,
        
        @NotBlank(message = "Memory type is required")
        String memoryType,
        
        @NotNull(message = "M.2 slots is required")
        @Min(value = 0, message = "M.2 slots must be at least 0")
        Integer m2Slots,
        
        @NotNull(message = "SATA ports is required")
        @Min(value = 0, message = "SATA ports must be at least 0")
        Integer sataPorts
) {}
