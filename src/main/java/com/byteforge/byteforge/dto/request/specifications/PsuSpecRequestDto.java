package com.byteforge.byteforge.dto.request.specifications;

public record PsuSpecRequestDto(
        String wattage,
        String efficiencyCert,
        String modularity,
        String pcie8pinConnectors,
        String sataConnectors
) {}
