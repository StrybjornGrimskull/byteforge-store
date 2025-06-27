package com.byteforge.byteforge.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;

import java.util.Set;


public record ConsumerRequestDto(
        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")

        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @Size(max = 255, message = "Email must not exceed 255 characters")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
        String password,

        @NotBlank(message = "Password confirmation is required")
        String confirmPassword
) {}