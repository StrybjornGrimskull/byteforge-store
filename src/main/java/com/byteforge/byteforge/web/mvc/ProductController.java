package com.byteforge.byteforge.web.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    @GetMapping("/list")
    public String listProductsPage() {
        return "product-list";
    }

    @GetMapping("/details/{id}")
    public String showProductDetails(@PathVariable Integer id) {
        return "product-details";
    }
}
