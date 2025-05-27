package com.byteforge.byteforge.dto.request;

import com.byteforge.byteforge.entities.ShoppingCart;

import java.math.BigDecimal;

public record ShoppingCartRequestDto (
    Integer productId,
    BigDecimal price,
    Integer customerId) {
    public static ShoppingCartRequestDto fromEntity(ShoppingCart shoppingCart) {
        return new ShoppingCartRequestDto(
                shoppingCart.getProduct().getId(),
                shoppingCart.getProduct().getPrice(),
                shoppingCart.getCustomer().getId()
        );
    }
}


