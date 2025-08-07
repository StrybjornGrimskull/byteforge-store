package com.byteforge.byteforge.dto.specifications;

import com.byteforge.byteforge.entities.specifications.CpuSpec;

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
) {

    public static CpuSpecDTO fromEntity(CpuSpec spec) {
        return new CpuSpecDTO(
                spec.getCores(),
                spec.getThreads(),
                spec.getBaseClock(),
                spec.getBoostClock(),
                spec.getSocket(),
                spec.getCacheSize(),
                spec.getTdp(),
                spec.getIntegratedGpu()
        );
    }
}
