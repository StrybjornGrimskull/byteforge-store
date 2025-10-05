package com.byteforge.byteforge.dto.response;

import java.time.LocalDate;

public record ProfileResponseDto(
        Integer userId,
        String firstName,
        String lastName,
        String phone,
        String city,
        String address,
        String postIndex,
        LocalDate birthDate,
        String phoneNumber,
        String email
) {}