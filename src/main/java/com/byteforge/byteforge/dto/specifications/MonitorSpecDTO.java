package com.byteforge.byteforge.dto.specifications;

import com.byteforge.byteforge.entities.specifications.MonitorSpec;
import java.math.BigDecimal;

public record MonitorSpecDTO(
        BigDecimal screenSize,
        String resolution,
        String panelType,
        Integer refreshRate,
        Integer responseTime
) implements ProductSpecDTO {

    public static MonitorSpecDTO fromEntity(MonitorSpec spec) {
        return new MonitorSpecDTO(
                spec.getScreenSize(),
                spec.getResolution(),
                spec.getPanelType(),
                spec.getRefreshRate(),
                spec.getResponseTime()
        );
    }
}
