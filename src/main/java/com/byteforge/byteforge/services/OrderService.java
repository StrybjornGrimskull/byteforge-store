package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.request.OrderRequestDto;
import com.byteforge.byteforge.entities.Order;

public interface OrderService {
    Order createOrder(String email, OrderRequestDto orderDto);
}