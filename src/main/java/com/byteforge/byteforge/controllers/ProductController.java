package com.byteforge.byteforge.controllers;

import com.byteforge.byteforge.dto.ProductResponseDTO;
import com.byteforge.byteforge.services.BrandService;
import com.byteforge.byteforge.services.CategoryService;
import com.byteforge.byteforge.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products/mvc")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService; // добавьте этот сервис, если нужно отображать список брендов

    @GetMapping("/list/json")
    @ResponseBody
    public Page<ProductResponseDTO> listProductsJson(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer brandId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size) {

        return productService.getProductsWithFilters(
                categoryId, brandId, minPrice, maxPrice, name, page, size);
    }
    @GetMapping("/list")
    public String listProductsPage(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer brandId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            Model model) {

        model.addAttribute("categoryId", categoryId);
        model.addAttribute("brandId", brandId);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);

        // Передаем бренды для выбранной категории
        model.addAttribute("brands", brandService.getBrandsByCategory(categoryId));
        model.addAttribute("categories", categoryService.getAllCategories());

        return "product-list";
    }
}
