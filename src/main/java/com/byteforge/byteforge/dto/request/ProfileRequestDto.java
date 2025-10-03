package com.byteforge.byteforge.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record ProfileRequestDto(
        @NotBlank(message = "First Name: First name is required")
        @Size(max = 100, message = "First Name: First name must not exceed 100 characters")
        String firstName,

        @NotBlank(message = "Last Name: Last name is required")
        @Size(max = 100, message = "Last Name: Last name must not exceed 100 characters")
        String lastName,

        @NotBlank(message = "Phone Number: Phone number is required")
        @Size(max = 20, message = "Phone Number: Phone must not exceed 20 characters")
        @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Phone Number: Phone must be a valid phone number")
        String phone,

        @NotBlank(message = "City: City is required")
        @Size(max = 255, message = "City: City must not exceed 255 characters")
        String city,

        @NotBlank(message = "Address: Address is required")
        @Size(max = 1000, message = "Address: Address must not exceed 1000 characters")
        String address,

        @NotNull(message = "Postal Code: Postal code is required")
        @Min(value = 10000, message = "Postal Code: Postal code must be at least 5 digits")
        @Max(value = 999999, message = "Postal Code: Postal code must be at most 6 digits")
        Integer postIndex,

        @NotNull(message = "Birth Date: Birth date is required")
        @PastOrPresent(message = "Birth Date: Birth date must be in the past or present")
        LocalDate birthDate
) {
}