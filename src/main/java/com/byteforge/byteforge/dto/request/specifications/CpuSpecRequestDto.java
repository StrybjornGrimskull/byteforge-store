package com.byteforge.byteforge.dto.request.specifications;

import jakarta.validation.constraints.NotBlank;

public record CpuSpecRequestDto(
        @NotBlank(message = "Number of cores cannot be empty") String cores,
        @NotBlank(message = "Number of threads cannot be empty") String threads,
        @NotBlank(message = "Base clock speed cannot be empty") String baseClock,
        @NotBlank(message = "Boost clock speed cannot be empty") String boostClock,
        @NotBlank(message = "Socket type cannot be empty") String socket,
        @NotBlank(message = "Cache size cannot be empty") String cacheSize,
        @NotBlank(message = "TDP cannot be empty") String tdp,
        String integratedGpu
) {}
