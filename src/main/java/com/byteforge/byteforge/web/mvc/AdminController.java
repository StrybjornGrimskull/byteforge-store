package com.byteforge.byteforge.web.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/dashboard")
public class AdminController {

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard() {
        return "admin-dashboard";
    }

    @GetMapping("/orders")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminOrders() {
        return "admin-orders";
    }

    @GetMapping("/order-details")
    @PreAuthorize("hasRole('ADMIN')")
    public String orderDetails(@RequestParam Long id, Model model) {
        model.addAttribute("orderId", id);
        return "order-details";
    }
}
