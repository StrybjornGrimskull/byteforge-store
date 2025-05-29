package com.byteforge.byteforge.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/wishlist")
    public String wishlist() {
        return "wishlist.html";
    }

    @GetMapping("/shopping-cart")
    public String shopping_cart() {
        return "shopping-cart.html";
    }

    @GetMapping("/checkout")
    public String checkout() {
        return "checkout.html";
    }
}