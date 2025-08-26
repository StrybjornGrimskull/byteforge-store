package com.byteforge.byteforge.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ActiveOrderDto(
    Long id,
    BigDecimal totalPrice,
    LocalDateTime date
) {}