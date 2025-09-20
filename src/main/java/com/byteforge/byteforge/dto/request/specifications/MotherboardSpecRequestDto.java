package com.byteforge.byteforge.dto.request.specifications;

public record MotherboardSpecRequestDto(
        String chipset,
        String memorySlots,
        String maxMemory,
        String m2Slots,
        String sataPorts
) {}
