package com.byteforge.byteforge.dto.request.specifications;

public record CpuSpecRequestDto(
        String socket,
        String cores,
        String threads,
        String baseClock,
        String boostClock,
        String cache,
        String tdp,
        String architecture,
        String processTechnology,
        String integratedGraphics,
        String memorySupport,
        String pcieSupport
) {}
