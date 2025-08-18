package com.byteforge.byteforge.web.mvc;

import com.byteforge.byteforge.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    // Страница: все купленные товары пользователя (с кнопкой 'Оставить отзыв')
    @GetMapping("/my-orders")
    public String myOrders(Model model, Authentication authentication) {
        model.addAllAttributes(reviewService.prepareMyOrdersModel(authentication.getName()));
        return "my-orders-reviews";
    }

    // Страница: форма создания отзыва
    @GetMapping("/create/{productId}")
    public String createReviewForm(@PathVariable Integer productId, Model model, Authentication authentication) {
            model.addAttribute("product", reviewService.getProductById(productId));
            model.addAttribute("productId", productId);
            return "review-form";
    }

    // Страница: отзывы по продукту
    @GetMapping("/product/{productId}")
    public String productReviews(@PathVariable Integer productId, Model model) {
        model.addAttribute("product", reviewService.getProductById(productId));
        model.addAttribute("productId", productId);
        model.addAttribute("reviews", reviewService.getActiveReviewsByProductId(productId));
        return "product-reviews";
    }
}