package com.byteforge.byteforge.dto.request.specifications;

import jakarta.validation.constraints.NotBlank;

public record RamSpecRequestDto(
        @NotBlank(message = "Memory size cannot be empty") String memorySize,
        @NotBlank(message = "Modules count cannot be empty") String modulesCount,
        @NotBlank(message = "Speed cannot be empty") String speed,
        @NotBlank(message = "Type cannot be empty") String type,
        @NotBlank(message = "Timings cannot be empty") String timings,
        @NotBlank(message = "Voltage cannot be empty") String voltage
) {}
