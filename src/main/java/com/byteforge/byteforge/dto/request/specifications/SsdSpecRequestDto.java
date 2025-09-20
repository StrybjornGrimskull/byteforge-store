package com.byteforge.byteforge.dto.request.specifications;

public record SsdSpecRequestDto(
        String capacity,
        String interfaceType,
        String readSpeed,
        String writeSpeed,
        String enduranceTbw,
        String dramCache,
        String encryption,
        String thickness
) {}
