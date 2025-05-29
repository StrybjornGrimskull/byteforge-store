package com.byteforge.byteforge.dto.response;

import com.byteforge.byteforge.dto.ProductDto;
import com.byteforge.byteforge.entities.OrderProduct;

/**
 * DTO for {@link OrderProduct}
 */
public record OrderProductResponseDto(
        Long orderId,
        Integer quantity,
        ProductDto product
) {
    public static OrderProductResponseDto fromEntity(OrderProduct orderProduct) {
        return new OrderProductResponseDto(
                orderProduct.getId(),
                orderProduct.getQuantity(),
                ProductDto.fromEntity(orderProduct.getProduct())
        );
    }
}
