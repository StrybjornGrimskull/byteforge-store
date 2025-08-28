package com.byteforge.byteforge.dto.response;

import jakarta.validation.constraints.Pattern;

public record CustomerNameFromProfileDto(
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                message = "Invalid input: only letters, numbers and spaces allowed")
        String firstName) {
}

