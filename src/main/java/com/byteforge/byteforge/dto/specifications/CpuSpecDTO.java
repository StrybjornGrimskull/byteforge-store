package com.byteforge.byteforge.dto.specifications;

import java.math.BigDecimal;

public record CpuSpecDTO(
        Integer cores,
        Integer threads,
        BigDecimal baseClock,
        BigDecimal boostClock,
        String socket,
        Integer cacheSize,
        Integer tdp,
        Boolean integratedGpu
) {}
