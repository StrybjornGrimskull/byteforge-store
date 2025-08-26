package com.byteforge.byteforge.dto;

import java.math.BigDecimal;

public record ProductDto(

        String name,
        BigDecimal price,
        String imageUrl
) {}
