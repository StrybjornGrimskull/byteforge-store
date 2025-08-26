package com.byteforge.byteforge.dto.specifications;

public record GpuSpecDTO(
        Integer memorySize,
        String memoryType,
        Integer busWidth,
        Integer baseClock,
        Integer boostClock,
        Integer tdp,
        Integer length,
        String displayOutputs
) {}
