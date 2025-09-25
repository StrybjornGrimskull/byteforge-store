package com.byteforge.byteforge.dto.request.specifications;

import jakarta.validation.constraints.NotBlank;

public record MotherboardSpecRequestDto(
        @NotBlank(message = "Socket type cannot be empty") String socket,
        @NotBlank(message = "Chipset cannot be empty") String chipset,
        @NotBlank(message = "Form factor cannot be empty") String formFactor,
        @NotBlank(message = "Memory slots cannot be empty") String memorySlots,
        @NotBlank(message = "Max memory cannot be empty") String maxMemory,
        @NotBlank(message = "Memory type cannot be empty") String memoryType,
        @NotBlank(message = "M.2 slots cannot be empty") String m2Slots,
        @NotBlank(message = "SATA ports cannot be empty") String sataPorts
) {}
