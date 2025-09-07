package com.byteforge.byteforge.web.api;

import com.byteforge.byteforge.dto.response.ReviewModerationDto;
import com.byteforge.byteforge.entities.Review;
import com.byteforge.byteforge.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    // API методы для модерации отзывов
    @GetMapping("/pending")
    public ResponseEntity<Page<ReviewModerationDto>> getPendingReviews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ReviewModerationDto> pendingReviews = reviewService.getPendingReviews(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(pendingReviews);
    }

    @GetMapping("/pending/count")
    public ResponseEntity<Long> getPendingReviewsCount() {
        long count = reviewService.getPendingReviewsCount();
        return ResponseEntity.status(HttpStatus.OK).body(count);
    }

    @PutMapping("/{reviewId}/approve")
    public ResponseEntity<Void> approveReview(@PathVariable Long reviewId) {
        reviewService.approveReview(reviewId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}