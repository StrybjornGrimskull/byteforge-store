package com.byteforge.byteforge.dto.request.specifications;

import jakarta.validation.constraints.NotBlank;

public record PsuSpecRequestDto(
        @NotBlank(message = "Wattage cannot be empty") String wattage,
        @NotBlank(message = "Form factor cannot be empty") String formFactor,
        @NotBlank(message = "Efficiency certification cannot be empty") String efficiencyCert,
        @NotBlank(message = "Modularity cannot be empty") String modularity,
        @NotBlank(message = "PCIe 8-pin connectors cannot be empty") String pcie8pinConnectors,
        @NotBlank(message = "SATA connectors cannot be empty") String sataConnectors
) {}
