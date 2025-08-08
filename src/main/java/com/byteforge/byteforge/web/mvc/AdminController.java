package com.byteforge.byteforge.web.mvc;

import com.byteforge.byteforge.dto.response.OrderResponseDto;
import com.byteforge.byteforge.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final OrderService orderService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard(Model model) {
        List<OrderResponseDto> activeOrders = orderService.getAllActiveOrders();
        model.addAttribute("activeOrders", activeOrders);
        return "admin";
    }
}
