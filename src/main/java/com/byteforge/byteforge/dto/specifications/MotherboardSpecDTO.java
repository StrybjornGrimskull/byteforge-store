package com.byteforge.byteforge.dto.specifications;

public record MotherboardSpecDTO(
        String socket,
        String chipset,
        String formFactor,
        Integer memorySlots,
        Integer maxMemory,
        String memoryType,
        Integer m2Slots,
        Integer sataPorts
) {}
