package com.byteforge.byteforge.web.mvc;

import com.byteforge.byteforge.entities.Customer;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.entities.Review;
import com.byteforge.byteforge.repositories.CustomerRepository;
import com.byteforge.byteforge.repositories.OrderProductRepository;
import com.byteforge.byteforge.repositories.ProductRepository;
import com.byteforge.byteforge.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final OrderProductRepository orderProductRepository;

    // Страница: все купленные товары пользователя (с кнопкой 'Оставить отзыв')
    @GetMapping("/my-orders")
    public String myOrders(Model model, Authentication authentication) {
        // Получаем текущего пользователя
        String email = authentication.getName();
        Optional<Customer> customerOpt = customerRepository.findByEmail(email);
        if (customerOpt.isEmpty()) return "redirect:/login";
        Customer customer = customerOpt.get();
        // Получаем id всех купленных товаров
        List<Integer> productIds = orderProductRepository.findProductIdsByCustomerId(customer.getId());
        List<Product> products = productRepository.findAllById(productIds);
        // Удаляем цикл с product.setReviewed(reviewed)
        Set<Integer> reviewedProductIds = reviewService.getReviewedProductIds(products, customer);
        model.addAttribute("products", products);
        model.addAttribute("customer", customer);
        model.addAttribute("reviewedProductIds", reviewedProductIds);
        return "my-orders-reviews";
    }

    // Страница: форма создания отзыва
    @GetMapping("/create/{productId}")
    public String createReviewForm(@PathVariable Integer productId, Model model, Authentication authentication) {
        String email = authentication.getName();
        Optional<Customer> customerOpt = customerRepository.findByEmail(email);
        if (customerOpt.isEmpty()) return "redirect:/login";
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) return "redirect:/reviews/my-orders";
        model.addAttribute("product", productOpt.get());
        model.addAttribute("review", new Review());
        return "review-form";
    }

    // Обработка формы создания отзыва
    @PostMapping("/create/{productId}")
    public String createReview(@PathVariable Integer productId, @ModelAttribute Review review, Authentication authentication, Model model) {
        String email = authentication.getName();
        Optional<Customer> customerOpt = customerRepository.findByEmail(email);
        if (customerOpt.isEmpty()) return "redirect:/login";
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) return "redirect:/reviews/my-orders";
        review.setProduct(productOpt.get());
        review.setCustomer(customerOpt.get());
        review.setUserFirstName(customerOpt.get().getProfile().getFirstName());
        review.setActive(false); // Отзыв неактивен до модерации
        try {
            reviewService.createReview(review);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("product", productOpt.get());
            return "review-form";
        }
        return "redirect:/reviews/my-orders";
    }

    // Страница: отзывы по продукту
    @GetMapping("/product/{productId}")
    public String productReviews(@PathVariable Integer productId, Model model) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) return "redirect:/";
        List<Review> reviews = reviewService.getActiveReviewsByProduct(productOpt.get());
        model.addAttribute("product", productOpt.get());
        model.addAttribute("reviews", reviews);
        return "product-reviews";
    }
} 