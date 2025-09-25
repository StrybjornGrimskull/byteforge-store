package com.byteforge.byteforge.services;

import com.byteforge.byteforge.constants.ApplicationConstants;
import com.byteforge.byteforge.dto.response.ProductResponseDto;
import com.byteforge.byteforge.dto.response.ReviewDto;
import com.byteforge.byteforge.dto.response.ReviewModerationDto;
import com.byteforge.byteforge.entities.Customer;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.entities.Review;
import com.byteforge.byteforge.repositories.CustomerRepository;
import com.byteforge.byteforge.repositories.OrderProductRepository;
import com.byteforge.byteforge.repositories.ProductRepository;
import com.byteforge.byteforge.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final OrderProductRepository orderProductRepository;

    @Transactional(readOnly = true)
    public Map<String, Object> prepareMyOrdersModel(String email) {
        Map<String, Object> model = new HashMap<>();
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(ApplicationConstants.CUSTOMER_NOT_FOUND));

        // Получаем уникальные ID продуктов напрямую из репозитория
        List<Integer> uniqueProductIds = orderProductRepository.findUniqueProductIdsByCustomerId(customer.getId());
        List<Product> products = productRepository.findAllById(uniqueProductIds);

        Set<Integer> reviewedProductIds = new HashSet<>();
        Map<Integer, Review> userReviews = new HashMap<>();
        
        for (Product product : products) {
            Optional<Review> existingReview = reviewRepository.findByProductAndCustomer(product, customer);
            if (existingReview.isPresent()) {
                reviewedProductIds.add(product.getId());
                userReviews.put(product.getId(), existingReview.get());
            }
        }

        model.put("products", products);
        model.put("customer", customer);
        model.put("reviewedProductIds", reviewedProductIds);
        model.put("userReviews", userReviews);
        return model;
    }

    @Transactional(readOnly = true)
    public boolean canCustomerReviewProduct(String email, Integer productId) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(ApplicationConstants.CUSTOMER_NOT_FOUND));
        
        // Check if customer has purchased the product
        List<Integer> uniquePurchasedProductIds = orderProductRepository.findUniqueProductIdsByCustomerId(customer.getId());
        boolean hasPurchased = uniquePurchasedProductIds.contains(productId);
        
        // Check if customer already reviewed the product
        boolean hasReviewed = reviewRepository.findReviewDtoByProductAndCustomer(productId, customer.getId()).isPresent();

        return hasPurchased && !hasReviewed;
    }

    @Transactional(readOnly = true)
    public ProductResponseDto getProductBasicInfo(Integer productId) {
        return productRepository.findProductResponseDtoById(productId);
    }

    @Transactional
    public Review createReview(String email, Integer productId, Review review) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(ApplicationConstants.CUSTOMER_NOT_FOUND));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException(ApplicationConstants.PRODUCT_NOT_FOUND));

        review.setProduct(product);
        review.setCustomer(customer);
        review.setUserFirstName(customer.getProfile().getFirstName());
        review.setActive(false); // Отзыв неактивен до модерации

        reviewRepository.save(review);
        return review;
    }

    @Transactional(readOnly = true)
    public List<ReviewDto> getActiveReviewsByProductId(Integer productId) {
        return reviewRepository.findActiveReviewDtosByProductId(productId);
    }


    @Transactional(readOnly = true)
    public Double getAverageRatingByProductId(Integer productId) {
        return reviewRepository.findAverageRatingByProductId(productId);
    }

    @Transactional(readOnly = true)
    public long getActiveReviewCountByProductId(Integer productId) {
        return reviewRepository.countByProductIdAndActiveTrue(productId);
    }

    // Методы для модерации отзывов
    @Transactional(readOnly = true)
    public Page<ReviewModerationDto> getPendingReviews(Pageable pageable) {
        return reviewRepository.findByActiveFalseOrderByCreatedAtDesc(pageable);
    }

    @Transactional(readOnly = true)
    public long getPendingReviewsCount() {
        return reviewRepository.countByActiveFalse();
    }

    @Transactional
    public void approveReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        review.setActive(true);
        reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}