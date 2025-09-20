package com.byteforge.byteforge.dto.request.specifications;

public record MonitorSpecRequestDto(
        String screenSize,
        String resolution,
        String panelType,
        String refreshRate,
        String responseTime
) {}
