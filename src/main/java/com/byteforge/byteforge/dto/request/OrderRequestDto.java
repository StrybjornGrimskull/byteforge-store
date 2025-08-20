package com.byteforge.byteforge.dto.request;

import jakarta.validation.constraints.*;

public record OrderRequestDto(
        @NotBlank(message = "First name is required")
        @Size(max = 100, message = "First name must be less than 100 characters")
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(max = 100, message = "Last name must be less than 100 characters")
        String lastName,

        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        @Size(max = 255, message = "Email must be less than 255 characters")
        String email,

        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "^\\+?\\d{10,15}$", message = "Invalid phone number")
        String phoneNumber,

        @NotBlank(message = "City is required")
        @Size(max = 100, message = "City must be less than 100 characters")
        String city,

        @NotBlank(message = "Address is required")
        @Size(max = 255, message = "Address must be less than 255 characters")
        String address,

        @NotNull(message = "Post index is required")
        @Min(value = 10000, message = "Invalid post index")
        @Max(value = 999999, message = "Invalid post index")
        Integer postIndex
) {}