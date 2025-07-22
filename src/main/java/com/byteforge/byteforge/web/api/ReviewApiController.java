package com.byteforge.byteforge.web.api;

import com.byteforge.byteforge.entities.Customer;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.entities.Review;
import com.byteforge.byteforge.repositories.CustomerRepository;
import com.byteforge.byteforge.repositories.ProductRepository;
import com.byteforge.byteforge.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewApiController {
    private final ReviewService reviewService;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getReviewsByProduct(@PathVariable Integer productId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(reviewService.getActiveReviewsByProduct(productOpt.get()));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Review>> getReviewsByCustomer(@PathVariable Integer customerId) {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(reviewService.getReviewsByCustomer(customerOpt.get()));
    }

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody Review review) {
        try {
            Review saved = reviewService.createReview(review);
            return ResponseEntity.ok(saved);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 