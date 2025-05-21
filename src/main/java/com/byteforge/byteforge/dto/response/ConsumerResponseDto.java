package com.byteforge.byteforge.dto.response;

import com.byteforge.byteforge.entities.Authority;
import com.byteforge.byteforge.entities.Customer;

import java.util.Set;
import java.util.stream.Collectors;

public record ConsumerResponseDto(
        Integer id,
        String email,
        Set<String> roles
) {
    public static ConsumerResponseDto fromEntity(Customer user) {
        return new ConsumerResponseDto(
                user.getId(),
                user.getEmail(),
                user.getAuthorities().stream()
                        .map(Authority::getName)
                        .collect(Collectors.toSet())
        );
    }
}