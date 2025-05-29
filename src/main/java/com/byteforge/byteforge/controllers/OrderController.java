//package com.byteforge.byteforge.controllers;
//
//import com.byteforge.byteforge.dto.request.OrderRequestDto;
//import com.byteforge.byteforge.dto.response.OrderResponseDto;
//import com.byteforge.byteforge.entities.Order;
//import com.byteforge.byteforge.services.OrderService;
//import com.byteforge.byteforge.services.OrderServiceImpl;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/orders")
//@RequiredArgsConstructor
//public class OrderController {
//
//    private final OrderServiceImpl orderServiceImpl;
//
//    @PostMapping
//    public ResponseEntity<OrderResponseDto> createOrder(
//            @RequestBody @Valid OrderRequestDto orderDto,
//            Authentication authentication) {
//        String email = authentication.getName();
//        Order order = orderServiceImpl.createOrder(email, OrderResponseDto);
//        return ResponseEntity.ok(OrderResponseDto);
//    }
//}
