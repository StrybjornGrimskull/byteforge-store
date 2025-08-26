package com.byteforge.byteforge.dto.response;

import com.byteforge.byteforge.entities.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link Order}
 */
public record OrderResponseDto(
        Long id,
        BigDecimal totalPrice,
        LocalDateTime date,
        String firstName,
        String lastName,
        String city,
        String address,
        String email,
        String phoneNumber,
        Integer postIndex,
        List<OrderProductResponseDto> orderProducts,
        Integer customer) {}