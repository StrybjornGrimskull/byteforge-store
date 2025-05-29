package com.byteforge.byteforge.dto.response;

import com.byteforge.byteforge.entities.OrderProduct;

/**
 * DTO for {@link OrderProduct}
 */
public record OrderProductResponseDto(
        Long id,
        Long orderId,
        Integer productId,
        Integer quantity
) {
    public static OrderProductResponseDto fromEntity(OrderProduct orderProduct) {
        return new OrderProductResponseDto(
                orderProduct.getId(),
                orderProduct.getId(),
                orderProduct.getProduct().getId(),
                orderProduct.getQuantity()
        );
    }
}
