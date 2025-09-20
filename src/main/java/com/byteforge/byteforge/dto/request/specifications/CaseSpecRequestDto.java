package com.byteforge.byteforge.dto.request.specifications;

public record CaseSpecRequestDto(
        String formFactor,
        String motherboardSupport,
        String maxGpuLength,
        String maxCpuCoolerHeight,
        String fansIncluded,
        String radiatorSupport
) {}
