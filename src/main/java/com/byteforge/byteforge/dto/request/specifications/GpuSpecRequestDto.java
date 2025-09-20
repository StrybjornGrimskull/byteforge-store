package com.byteforge.byteforge.dto.request.specifications;

public record GpuSpecRequestDto(
        String gpuChipset,
        String memorySize,
        String memoryType,
        String memoryBus,
        String gpuBaseClock,
        String gpuBoostClock,
        String cudaCores,
        String streamProcessors,
        String gpuTdp,
        String recommendedPsu,
        String outputs,
        String cooling,
        String length,
        String width,
        String height
) {}
