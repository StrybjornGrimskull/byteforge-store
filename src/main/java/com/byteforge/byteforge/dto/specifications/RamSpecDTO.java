package com.byteforge.byteforge.dto.specifications;

import java.math.BigDecimal;

public record RamSpecDTO(
        Integer memorySize,
        Integer modulesCount,
        Integer speed,
        String type,
        String timings,
        BigDecimal voltage
) {}
