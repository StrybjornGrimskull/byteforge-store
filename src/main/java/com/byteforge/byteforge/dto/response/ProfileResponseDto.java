package com.byteforge.byteforge.dto.response;

import java.time.LocalDate;

public record ProfileResponseDto(
        Integer userId,
        String firstName,
        String lastName,
        String phone,
        String city,
        String address,
        Integer postIndex,
        LocalDate birthDate,
        String phoneNumber,
        String email
) {}