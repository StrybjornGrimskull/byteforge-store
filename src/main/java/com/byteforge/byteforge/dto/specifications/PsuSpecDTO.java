package com.byteforge.byteforge.dto.specifications;

public record PsuSpecDTO(
        Integer wattage,
        String formFactor,
        String efficiencyCert,
        String modularity,
        Integer pcie8pinConnectors,
        Integer sataConnectors
) {}
