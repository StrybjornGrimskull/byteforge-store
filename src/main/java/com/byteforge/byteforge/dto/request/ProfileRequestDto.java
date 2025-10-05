package com.byteforge.byteforge.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record ProfileRequestDto(
        @NotNull(message = "First Name: First name is required")
        @NotBlank(message = "First Name: First name cannot be empty")
        @Size(max = 100, message = "First Name: First name must not exceed 100 characters")
        String firstName,

        @NotNull(message = "Last Name: Last name is required")
        @NotBlank(message = "Last Name: Last name cannot be empty")
        @Size(max = 100, message = "Last Name: Last name must not exceed 100 characters")
        String lastName,

        @NotNull(message = "Phone Number: Phone number is required")
        @NotBlank(message = "Phone Number: Phone number cannot be empty")
        @Size(min = 10, max = 15, message = "Phone Number: Phone number must be between 10 and 15 digits")
        String phone,

        @NotNull(message = "City: City is required")
        @NotBlank(message = "City: City cannot be empty")
        @Size(max = 255, message = "City: City must not exceed 255 characters")
        String city,

        @NotNull(message = "Address: Address is required")
        @NotBlank(message = "Address: Address cannot be empty")
        @Size(max = 1000, message = "Address: Address must not exceed 1000 characters")
        String address,

        @NotNull(message = "Postal Code: Postal code is required")
        @NotBlank(message = "Postal Code: Postal code cannot be empty")
        @Size(min = 5, max = 6, message = "Postal Code: Postal code must be between 5 and 6 digits")
        @Pattern(regexp = "^\\d{5,6}$", message = "Postal Code: Postal code must contain only digits")
        String postIndex,

        @NotNull(message = "Birth Date: Birth date is required")
        @PastOrPresent(message = "Birth Date: Birth date must be in the past or present")
        LocalDate birthDate
) {
}