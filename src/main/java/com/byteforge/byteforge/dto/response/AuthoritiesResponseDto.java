package com.byteforge.byteforge.dto.response;


import com.byteforge.byteforge.entities.Authority;

public record AuthoritiesResponseDto(
        Integer id,
        String name
) {
    public static AuthoritiesResponseDto fromEntity(Authority authority) {
        return new AuthoritiesResponseDto(
                authority.getId(),
                authority.getName()
        );
    }
}