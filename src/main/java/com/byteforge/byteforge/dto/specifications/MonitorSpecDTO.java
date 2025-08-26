package com.byteforge.byteforge.dto.specifications;

import java.math.BigDecimal;

public record MonitorSpecDTO(
        BigDecimal screenSize,
        String resolution,
        String panelType,
        Integer refreshRate,
        Integer responseTime
) {}
