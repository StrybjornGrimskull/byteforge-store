package com.byteforge.byteforge.dto.specifications;

public record CaseSpecDTO(
        String formFactor,
        String motherboardSupport,
        Integer maxGpuLength,
        Integer maxCpuCoolerHeight,
        Integer fansIncluded,
        String radiatorSupport
) {}
