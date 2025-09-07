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
    public String ordersManagement() {
        return "admin-orders-management";
    }
    
    @GetMapping("/orders/active")
    @PreAuthorize("hasRole('ADMIN')")
    public String activeOrders() {
        return "admin-active-orders";
    }
    
    @GetMapping("/orders/archived")
    @PreAuthorize("hasRole('ADMIN')")
    public String archivedOrders() {
        return "admin-archived-orders";
    }

    @GetMapping("/orders/active/order-details")
    @PreAuthorize("hasRole('ADMIN')")
    public String activeOrderDetails(@RequestParam Long id, Model model) {
        model.addAttribute("orderId", id);
        return "admin-active-order-details";

    }     @GetMapping("/orders/archived/order-details")
    @PreAuthorize("hasRole('ADMIN')")
    public String archivedOrderDetails(@RequestParam Long id, Model model) {
        model.addAttribute("orderId", id);
        return "admin-archived-order-details";
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public String usersManagement() {
        return "admin-users";
    }

    @GetMapping("/reviews")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public String reviewsModeration() {
        return "admin-reviews-moderation";
    }
}
