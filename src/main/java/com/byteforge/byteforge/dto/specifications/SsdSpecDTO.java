package com.byteforge.byteforge.dto.specifications;

import java.math.BigDecimal;

public record SsdSpecDTO(
        Integer capacity,
        String formFactor,
        String interfaceType,
        Integer readSpeed,
        Integer writeSpeed,
        String memoryType,
        Integer enduranceTbw,
        Boolean dramCache,
        String encryption,
        BigDecimal thickness
) {}
