package com.byteforge.byteforge.dto.specifications;

import com.byteforge.byteforge.entities.GpuSpec;

public record GpuSpecDTO(
        Integer memorySize,
        String memoryType,
        Integer busWidth,
        Integer baseClock,
        Integer boostClock,
        Integer tdp,
        Integer length,
        String displayOutputs
) implements ProductSpecDTO {

    public static GpuSpecDTO fromEntity(GpuSpec spec) {
        return new GpuSpecDTO(
                spec.getMemorySize(),
                spec.getMemoryType(),
                spec.getBusWidth(),
                spec.getBaseClock(),
                spec.getBoostClock(),
                spec.getTdp(),
                spec.getLength(),
                spec.getDisplayOutputs()
        );
    }
}
