package com.byteforge.byteforge.web.mvc;

import com.byteforge.byteforge.dto.response.ProductResponseDto;
import com.byteforge.byteforge.services.BrandService;
import com.byteforge.byteforge.services.CategoryService;
import com.byteforge.byteforge.services.ProductService;
import com.byteforge.byteforge.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final ReviewService reviewService;

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

    @GetMapping("/details/{id}")
    public String showProductDetails(@PathVariable Integer id, Model model) {
        ProductResponseDto product = productService.getProductById(id);
        Double averageRating = reviewService.getAverageRatingByProductId(product.id());
        long reviewCount = reviewService.getActiveReviewCountByProductId(product.id());
        model.addAttribute("product", product);
        model.addAttribute("averageRating", averageRating);
        model.addAttribute("reviewCount", reviewCount);
        return "product-details";
    }
}
