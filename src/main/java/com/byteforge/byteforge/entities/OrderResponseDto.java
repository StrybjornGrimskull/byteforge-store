package com.byteforge.byteforge.entities;

import com.byteforge.byteforge.dto.response.OrderProductResponseDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO for {@link Order}
 */
public record OrderResponseDto(
        Long id,
        Double totalPrice,
        LocalDateTime date,
        String firstName,
        String lastName,
        String city,
        String address,
        String email,
        String phoneNumber,
        Integer postIndex,
        List<OrderProductResponseDto> orderProducts,
        Integer customer) {

    public static OrderResponseDto fromEntity(Order order) {
        List<OrderProductResponseDto> orderProductDtos = null;
        if (order.getOrderProducts() != null) {
            orderProductDtos = order.getOrderProducts().stream()
                    .map(OrderProductResponseDto::fromEntity)
                    .collect(Collectors.toList());
        }
        return new OrderResponseDto(
                order.getId(),
                order.getTotalPrice(),
                order.getDate(),
                order.getFirstName(),
                order.getLastName(),
                order.getCity(),
                order.getAddress(),
                order.getEmail(),
                order.getPhoneNumber(),
                order.getPostIndex(),
                orderProductDtos,
                order.getCustomer().getId()
        );
    }
}