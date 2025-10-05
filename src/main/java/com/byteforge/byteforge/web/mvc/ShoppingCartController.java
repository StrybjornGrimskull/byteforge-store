package com.byteforge.byteforge.web.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class ShoppingCartController {

    @GetMapping("/shopping-cart")
    public String shoppingCart() {
        return "shopping-cart";
    }

    @GetMapping("/checkout")
    public String checkout() {
        return "checkout";
    }
}
