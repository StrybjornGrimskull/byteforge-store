package com.byteforge.byteforge.dto.request;

import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ProfileRequestDto(
        @Size(max = 100, message = "First name must not exceed 100 characters")
        String firstName,

        @Size(max = 100, message = "Last name must not exceed 100 characters")
        String lastName,

        @Size(max = 20, message = "Phone must not exceed 20 characters")
        @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$|^$", message = "Phone must be a valid phone number or empty")
        String phone,

        @Size(max = 255, message = "City must not exceed 255 characters")
        String city,

        @Size(max = 1000, message = "Address must not exceed 1000 characters")
        String address,

        Integer postIndex,

        @PastOrPresent(message = "Birth date must be in the past or present")
        LocalDate birthDate
) {
}