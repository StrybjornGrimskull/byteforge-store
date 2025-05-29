package com.byteforge.byteforge.controllers;

import com.byteforge.byteforge.dto.request.OrderRequestDto;
import com.byteforge.byteforge.dto.response.OrderResponseDto;
import com.byteforge.byteforge.services.OrderServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderServiceImpl orderServiceImpl;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            @RequestBody @Valid OrderRequestDto orderDto,
            Authentication authentication) {
        String email = authentication.getName();
        OrderResponseDto response = orderServiceImpl.createOrder(email, orderDto);
        return ResponseEntity.ok(response);
    }
}