package com.byteforge.byteforge.dto.request.specifications;

import jakarta.validation.constraints.NotBlank;

public record MonitorSpecRequestDto(
        @NotBlank(message = "Screen size cannot be empty") String screenSize,
        @NotBlank(message = "Resolution cannot be empty") String resolution,
        @NotBlank(message = "Panel type cannot be empty") String panelType,
        @NotBlank(message = "Refresh rate cannot be empty") String refreshRate,
        @NotBlank(message = "Response time cannot be empty") String responseTime
) {}
