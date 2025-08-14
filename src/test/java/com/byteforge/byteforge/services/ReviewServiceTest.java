package com.byteforge.byteforge.services;

import com.byteforge.byteforge.entities.Customer;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.entities.Review;
import com.byteforge.byteforge.repositories.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceTest {
    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetActiveReviewsByProduct() {
        Product product = new Product();
        List<Review> reviews = Arrays.asList(new Review(), new Review());
        when(reviewRepository.findByProductAndActiveTrue(product)).thenReturn(reviews);
        List<Review> result = reviewService.getActiveReviewsByProduct(product);
        assertEquals(2, result.size());
        verify(reviewRepository).findByProductAndActiveTrue(product);
    }

    @Test
    void testGetReviewsByCustomer() {
        Customer customer = new Customer();
        List<Review> reviews = Arrays.asList(new Review(), new Review());
        when(reviewRepository.findByCustomer(customer)).thenReturn(reviews);
        List<Review> result = reviewService.getReviewsByCustomer(customer);
        assertEquals(2, result.size());
        verify(reviewRepository).findByCustomer(customer);
    }

    @Test
    void testHasReviewByCustomer_True() {
        Product product = new Product();
        Customer customer = new Customer();
        customer.setId(1);
        Review review = new Review();
        review.setCustomer(customer);
        product.setReviews(List.of(review));
        boolean result = reviewRepository.existsByProductAndCustomer(product, customer);
        assertTrue(result);
    }

    @Test
    void testHasReviewByCustomer_False() {
        Product product = new Product();
        Customer customer = new Customer();
        customer.setId(1);
        product.setReviews(Collections.emptyList());
        boolean result = reviewRepository.existsByProductAndCustomer(product, customer);
        assertFalse(result);
    }

    @Test
    void testCreateReview_Success() {
        Review review = new Review();
        Product product = new Product();
        Customer customer = new Customer();
        review.setProduct(product);
        review.setCustomer(customer);
        when(reviewRepository.findByProductAndCustomer(product, customer)).thenReturn(Optional.empty());
        when(reviewRepository.save(review)).thenReturn(review);
        Review saved = reviewService.createReview(review);
        assertEquals(review, saved);
        verify(reviewRepository).findByProductAndCustomer(product, customer);
        verify(reviewRepository).save(review);
    }

    @Test
    void testCreateReview_AlreadyExists() {
        Review review = new Review();
        Product product = new Product();
        Customer customer = new Customer();
        review.setProduct(product);
        review.setCustomer(customer);
        when(reviewRepository.findByProductAndCustomer(product, customer)).thenReturn(Optional.of(new Review()));
        assertThrows(IllegalStateException.class, () -> reviewService.createReview(review));
        verify(reviewRepository).findByProductAndCustomer(product, customer);
        verify(reviewRepository, never()).save(any());
    }

    @Test
    void testGetReviewedProductIds() {
        Customer customer = new Customer();
        customer.setId(1);
        Product product1 = new Product();
        product1.setId(1);
        Product product2 = new Product();
        product2.setId(2);
        Review review = new Review();
        review.setCustomer(customer);
        product1.setReviews(List.of(review));
        product2.setReviews(Collections.emptyList());
        List<Product> products = List.of(product1, product2);
        Set<Integer> reviewedIds = reviewService.getReviewedProductIds(products, customer);
        assertTrue(reviewedIds.contains(1));
        assertFalse(reviewedIds.contains(2));
    }

    // Тесты для рейтинга и количества отзывов уже реализованы выше
    @Test
    void testGetAverageRatingByProductId() {
        Integer productId = 1;
        when(reviewRepository.findAverageRatingByProductId(productId)).thenReturn(4.5);
        Double avg = reviewService.getAverageRatingByProductId(productId);
        assertNotNull(avg);
        assertEquals(4.5, avg);
        verify(reviewRepository).findAverageRatingByProductId(productId);
    }

    @Test
    void testGetAverageRatingByProductId_NoReviews() {
        Integer productId = 2;
        when(reviewRepository.findAverageRatingByProductId(productId)).thenReturn(null);
        Double avg = reviewService.getAverageRatingByProductId(productId);
        assertNull(avg);
        verify(reviewRepository).findAverageRatingByProductId(productId);
    }

    @Test
    void testGetActiveReviewCountByProductId() {
        Integer productId = 1;
        when(reviewRepository.countByProductIdAndActiveTrue(productId)).thenReturn(5L);
        long count = reviewService.getActiveReviewCountByProductId(productId);
        assertEquals(5L, count);
        verify(reviewRepository).countByProductIdAndActiveTrue(productId);
    }

    @Test
    void testGetActiveReviewCountByProductId_NoReviews() {
        Integer productId = 2;
        when(reviewRepository.countByProductIdAndActiveTrue(productId)).thenReturn(0L);
        long count = reviewService.getActiveReviewCountByProductId(productId);
        assertEquals(0L, count);
        verify(reviewRepository).countByProductIdAndActiveTrue(productId);
    }
} 