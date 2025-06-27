package com.byteforge.byteforge.dto.response;

import com.byteforge.byteforge.entities.Profile;

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
) {
    public static ProfileResponseDto fromEntity(Profile profile) {
        return new ProfileResponseDto(
                profile.getCustomerId(),
                profile.getFirstName(),
                profile.getLastName(),
                profile.getPhone(),
                profile.getCity(),
                profile.getAddress(),
                profile.getPostIndex(),
                profile.getBirthDate(),
                profile.getPhone(),
                profile.getCustomer().getEmail()
        );
    }
}