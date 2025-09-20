package com.byteforge.byteforge.dto.request.specifications;

public record RamSpecRequestDto(
        String modulesCount,
        String speed,
        String type,
        String timings,
        String voltage
) {}
