package com.byteforge.byteforge.dto.specifications;

import com.byteforge.byteforge.entities.specifications.SsdSpec;
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
) implements ProductSpecDTO {

    public static SsdSpecDTO fromEntity(SsdSpec spec) {
        return new SsdSpecDTO(
                spec.getCapacity(),
                spec.getFormFactor(),
                spec.getInterfaceType(),
                spec.getReadSpeed(),
                spec.getWriteSpeed(),
                spec.getMemoryType(),
                spec.getEnduranceTbw(),
                spec.getDramCache(),
                spec.getEncryption(),
                spec.getThickness()
        );
    }
}
