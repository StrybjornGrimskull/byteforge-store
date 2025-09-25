package com.byteforge.byteforge.dto.request.specifications;

import jakarta.validation.constraints.NotBlank;

public record SsdSpecRequestDto(
        @NotBlank(message = "Capacity cannot be empty") String capacity,
        @NotBlank(message = "Form factor cannot be empty") String formFactor,
        @NotBlank(message = "Interface type cannot be empty") String interfaceType,
        @NotBlank(message = "Read speed cannot be empty") String readSpeed,
        @NotBlank(message = "Write speed cannot be empty") String writeSpeed,
        @NotBlank(message = "Memory type cannot be empty") String memoryType,
        @NotBlank(message = "Endurance TBW cannot be empty") String enduranceTbw,
        String dramCache,
        String encryption,
        String thickness
) {}
