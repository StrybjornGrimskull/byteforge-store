package com.byteforge.byteforge.web.mvc;

import com.byteforge.byteforge.dto.response.OrderResponseDto;
import com.byteforge.byteforge.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/history")
    public String showOrderHistory(Authentication authentication, Model model) {
        if (authentication == null) {
            return "redirect:/auth/login";
        }
        String email = authentication.getName();
        List<OrderResponseDto> completedOrders = orderService.getCompletedOrdersForUser(email);
        model.addAttribute("orders", completedOrders);
        return "order-history";
    }
} 