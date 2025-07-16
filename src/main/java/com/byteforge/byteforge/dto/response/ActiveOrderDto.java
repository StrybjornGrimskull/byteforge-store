package com.byteforge.byteforge.dto.response;

import com.byteforge.byteforge.entities.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ActiveOrderDto(
    Long id,
    BigDecimal totalPrice,
    LocalDateTime date
) {
    public static ActiveOrderDto fromEntity(Order order) {
        return new ActiveOrderDto(order.getId(), order.getTotalPrice(), order.getDate());
    }
} 