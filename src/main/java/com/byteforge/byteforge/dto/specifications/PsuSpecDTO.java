package com.byteforge.byteforge.dto.specifications;

import com.byteforge.byteforge.entities.specifications.PsuSpec;

public record PsuSpecDTO(
        Integer wattage,
        String formFactor,
        String efficiencyCert,
        String modularity,
        Integer pcie8pinConnectors,
        Integer sataConnectors
) {

    public static PsuSpecDTO fromEntity(PsuSpec spec) {
        return new PsuSpecDTO(
                spec.getWattage(),
                spec.getFormFactor(),
                spec.getEfficiencyCert(),
                spec.getModularity(),
                spec.getPcie8pinConnectors(),
                spec.getSataConnectors()
        );
    }
}
