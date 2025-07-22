package com.byteforge.byteforge.services;

import com.byteforge.byteforge.entities.Customer;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.entities.Review;
import com.byteforge.byteforge.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public List<Review> getActiveReviewsByProduct(Product product) {
        return reviewRepository.findByProductAndActiveTrue(product);
    }

    public List<Review> getReviewsByCustomer(Customer customer) {
        return reviewRepository.findByCustomer(customer);
    }

    public boolean hasReviewByCustomer(Product product, Customer customer) {
        return product.getReviews().stream()
            .anyMatch(r -> r.getCustomer().getId().equals(customer.getId()));
    }

    @Transactional
    public Review createReview(Review review) {
        // Проверка: не оставлял ли пользователь уже отзыв на этот продукт
        if (reviewRepository.findByProductAndCustomer(review.getProduct(), review.getCustomer()).isPresent()) {
            throw new IllegalStateException("Пользователь уже оставил отзыв на этот продукт");
        }
        return reviewRepository.save(review);
    }

    public Set<Integer> getReviewedProductIds(List<Product> products, Customer customer) {
        Set<Integer> reviewedIds = new HashSet<>();
        for (Product product : products) {
            boolean reviewed = hasReviewByCustomer(product, customer);
            if (reviewed) {
                reviewedIds.add(product.getId());
            }
        }
        return reviewedIds;
    }

    public Double getAverageRatingByProductId(Integer productId) {
        return reviewRepository.findAverageRatingByProductId(productId);
    }

    public long getActiveReviewCountByProductId(Integer productId) {
        return reviewRepository.countByProductIdAndActiveTrue(productId);
    }
} 