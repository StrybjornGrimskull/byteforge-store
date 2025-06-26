package com.byteforge.byteforge.dto.specifications;

import com.byteforge.byteforge.entities.specifications.MotherboardSpec;

public record MotherboardSpecDTO(
        String socket,
        String chipset,
        String formFactor,
        Integer memorySlots,
        Integer maxMemory,
        String memoryType,
        Integer m2Slots,
        Integer sataPorts
) implements ProductSpecDTO {

    public static MotherboardSpecDTO fromEntity(MotherboardSpec spec) {
        return new MotherboardSpecDTO(
                spec.getSocket(),
                spec.getChipset(),
                spec.getFormFactor(),
                spec.getMemorySlots(),
                spec.getMaxMemory(),
                spec.getMemoryType(),
                spec.getM2Slots(),
                spec.getSataPorts()
        );
    }
}
