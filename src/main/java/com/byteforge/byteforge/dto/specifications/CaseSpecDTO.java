package com.byteforge.byteforge.dto.specifications;

import com.byteforge.byteforge.entities.specifications.CaseSpec;

public record CaseSpecDTO(
        String formFactor,
        String motherboardSupport,
        Integer maxGpuLength,
        Integer maxCpuCoolerHeight,
        Integer fansIncluded,
        String radiatorSupport
) implements ProductSpecDTO {

    public static CaseSpecDTO fromEntity(CaseSpec spec) {
        return new CaseSpecDTO(
                spec.getFormFactor(),
                spec.getMotherboardSupport(),
                spec.getMaxGpuLength(),
                spec.getMaxCpuCoolerHeight(),
                spec.getFansIncluded(),
                spec.getRadiatorSupport()
        );
    }
}
