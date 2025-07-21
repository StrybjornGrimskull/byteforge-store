package com.byteforge.byteforge.web.api;

import com.byteforge.byteforge.dto.request.OrderRequestDto;
import com.byteforge.byteforge.dto.response.ActiveOrderDto;
import com.byteforge.byteforge.dto.response.OrderResponseDto;
import com.byteforge.byteforge.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            @RequestBody @Valid OrderRequestDto orderDto,
            Authentication authentication) {
        String email = authentication.getName();
        OrderResponseDto response = orderService.createOrder(email, orderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/active")
    public ResponseEntity<List<ActiveOrderDto>> getActiveOrders(Authentication authentication) {
        String email = authentication.getName();
        List<ActiveOrderDto> orders = orderService.getActiveOrdersForUser(email);
        return ResponseEntity.ok(orders);
    }
}