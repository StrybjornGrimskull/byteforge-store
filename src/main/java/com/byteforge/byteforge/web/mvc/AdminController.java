package com.byteforge.byteforge.web.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "admin-orders" ;
    }
}
