package com.byteforge.byteforge.dto.request.specifications;

import jakarta.validation.constraints.NotBlank;

public record CaseSpecRequestDto(
        @NotBlank(message = "Form factor cannot be empty") String formFactor,
        @NotBlank(message = "Motherboard support cannot be empty") String motherboardSupport,
        @NotBlank(message = "Max GPU length cannot be empty") String maxGpuLength,
        @NotBlank(message = "Max CPU cooler height cannot be empty") String maxCpuCoolerHeight,
        @NotBlank(message = "Fans included cannot be empty") String fansIncluded,
        @NotBlank(message = "Radiator support cannot be empty") String radiatorSupport
) {}
