package com.byteforge.byteforge.dto.specifications;

import com.byteforge.byteforge.entities.RamSpec;
import java.math.BigDecimal;

public record RamSpecDTO(
        Integer memorySize,
        Integer modulesCount,
        Integer speed,
        String type,
        String timings,
        BigDecimal voltage
) implements ProductSpecDTO {

    public static RamSpecDTO fromEntity(RamSpec spec) {
        return new RamSpecDTO(
                spec.getMemorySize(),
                spec.getModulesCount(),
                spec.getSpeed(),
                spec.getType(),
                spec.getTimings(),
                spec.getVoltage()
        );
    }
}
