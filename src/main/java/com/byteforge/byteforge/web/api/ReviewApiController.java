package com.byteforge.byteforge.web.api;

import com.byteforge.byteforge.entities.Review;
import com.byteforge.byteforge.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewApiController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Review> createReview(
            @RequestBody Review review,
            Authentication authentication) {
             Review savedReview = reviewService.createReview(authentication.getName(), review.getProduct().getId(), review);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
           }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getProductReviews(@PathVariable Integer productId) {
        List<Review> reviews = reviewService.getActiveReviewsByProductId(productId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/can-review/{productId}")
    public ResponseEntity<Boolean> canReviewProduct(
            @PathVariable Integer productId,
            Authentication authentication) {
        boolean canReview = reviewService.canCustomerReviewProduct(authentication.getName(), productId);
        return ResponseEntity.ok(canReview);
    }
}