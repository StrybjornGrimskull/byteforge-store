package com.byteforge.byteforge.dto.request.specifications;

import jakarta.validation.constraints.NotBlank;

public record GpuSpecRequestDto(
        @NotBlank(message = "Memory size cannot be empty") String memorySize,
        @NotBlank(message = "Memory type cannot be empty") String memoryType,
        @NotBlank(message = "Memory bus width cannot be empty") String busWidth,
        @NotBlank(message = "Base clock cannot be empty") String baseClock,
        @NotBlank(message = "Boost clock cannot be empty") String boostClock,
        @NotBlank(message = "TDP cannot be empty") String tdp,
        @NotBlank(message = "Length cannot be empty") String length,
        @NotBlank(message = "Display outputs cannot be empty") String displayOutputs
) {}
