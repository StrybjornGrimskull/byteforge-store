package com.byteforge.byteforge.services;

import com.byteforge.byteforge.constants.ApplicationConstants;
import com.byteforge.byteforge.entities.Customer;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.entities.Review;
import com.byteforge.byteforge.repositories.CustomerRepository;
import com.byteforge.byteforge.repositories.OrderProductRepository;
import com.byteforge.byteforge.repositories.ProductRepository;
import com.byteforge.byteforge.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
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

        List<Integer> productIds = orderProductRepository.findProductIdsByCustomerId(customer.getId());
        List<Product> products = productRepository.findAllById(productIds);

        Set<Integer> reviewedProductIds = new HashSet<>();
        for (Product product : products) {
            if (reviewRepository.existsByProductAndCustomer(product, customer)) {
                reviewedProductIds.add(product.getId());
            }
        }

        model.put("products", products);
        model.put("customer", customer);
        model.put("reviewedProductIds", reviewedProductIds);
        return model;
    }

    @Transactional(readOnly = true)
    public boolean canCustomerReviewProduct(String email, Integer productId) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(ApplicationConstants.CUSTOMER_NOT_FOUND));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException(ApplicationConstants.PRODUCT_NOT_FOUND));

        // Check if customer has purchased the product
        List<Integer> purchasedProductIds = orderProductRepository.findProductIdsByCustomerId(customer.getId());
        boolean hasPurchased = purchasedProductIds.contains(productId);
        // Check if customer already reviewed the product
        boolean hasReviewed = reviewRepository.findByProductAndCustomer(product, customer).isPresent();

        return hasPurchased && !hasReviewed;
    }

    @Transactional(readOnly = true)
    public Product getProductById(Integer productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException(ApplicationConstants.PRODUCT_NOT_FOUND));
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
    public List<Review> getActiveReviewsByProductId(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException(ApplicationConstants.PRODUCT_NOT_FOUND));
        return reviewRepository.findByProductAndActiveTrue(product);
    }

    @Transactional(readOnly = true)
    public List<Review> getActiveReviewsByProduct(Product product) {
        return reviewRepository.findByProductAndActiveTrue(product);
    }

    @Transactional(readOnly = true)
    public List<Review> getReviewsByCustomer(Customer customer) {
        return reviewRepository.findByCustomer(customer);
    }

    @Transactional
    public Review createReview(Review review) {
        // Check if user already reviewed this product
        if (reviewRepository.findByProductAndCustomer(review.getProduct(), review.getCustomer()).isPresent()) {
            throw new IllegalStateException("User already reviewed this product");
        }
        return reviewRepository.save(review);
    }

    @Transactional(readOnly = true)
    public Set<Integer> getReviewedProductIds(List<Product> products, Customer customer) {
        Set<Integer> reviewedIds = new HashSet<>();
        for (Product product : products) {
            boolean reviewed = reviewRepository.existsByProductAndCustomer(product, customer);
            if (reviewed) {
                reviewedIds.add(product.getId());
            }
        }
        return reviewedIds;
    }

    @Transactional(readOnly = true)
    public Double getAverageRatingByProductId(Integer productId) {
        return reviewRepository.findAverageRatingByProductId(productId);
    }

    @Transactional(readOnly = true)
    public long getActiveReviewCountByProductId(Integer productId) {
        return reviewRepository.countByProductIdAndActiveTrue(productId);
    }
}