package com.byteforge.byteforge.dto.specifications;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PsuSpecDTO(
        @NotNull(message = "Wattage is required")
        @Min(value = 1, message = "Wattage must be at least 1")
        Integer wattage,
        
        @NotBlank(message = "Form factor is required")
        String formFactor,
        
        @NotBlank(message = "Efficiency certification is required")
        String efficiencyCert,
        
        @NotBlank(message = "Modularity is required")
        String modularity,
        
        @NotNull(message = "PCIe 8-pin connectors is required")
        @Min(value = 0, message = "PCIe 8-pin connectors must be at least 0")
        Integer pcie8pinConnectors,
        
        @NotNull(message = "SATA connectors is required")
        @Min(value = 0, message = "SATA connectors must be at least 0")
        Integer sataConnectors
) {}
