package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.request.OrderRequestDto;
import com.byteforge.byteforge.dto.response.OrderResponseDto;

public interface OrderService {
    OrderResponseDto createOrder(String email, OrderRequestDto orderDto);
}