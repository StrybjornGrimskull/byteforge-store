package com.byteforge.byteforge.services;

import com.byteforge.byteforge.constants.ApplicationConstants;
import com.byteforge.byteforge.dto.response.ProductResponseDto;
import com.byteforge.byteforge.dto.response.ReviewDto;
import com.byteforge.byteforge.dto.response.ReviewModerationDto;
import com.byteforge.byteforge.entities.Customer;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.entities.Profile;
import com.byteforge.byteforge.entities.Review;
import com.byteforge.byteforge.repositories.CustomerRepository;
import com.byteforge.byteforge.repositories.OrderProductRepository;
import com.byteforge.byteforge.repositories.ProductRepository;
import com.byteforge.byteforge.repositories.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private OrderProductRepository orderProductRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private ReviewService reviewService;

    private Customer testCustomer;
    private Product testProduct;
    private Review testReview;
    private ReviewDto testReviewDto;
    private ReviewModerationDto testReviewModerationDto;
    private ProductResponseDto testProductResponseDto;

    @BeforeEach
    void setUp() {
        // Создаем тестовый профиль
        Profile testProfile = new Profile();
        testProfile.setFirstName("John");
        testProfile.setLastName("Doe");

        // Создаем тестового клиента
        testCustomer = new Customer();
        testCustomer.setId(1);
        testCustomer.setEmail("test@example.com");
        testCustomer.setProfile(testProfile);

        // Создаем тестовый продукт
        testProduct = new Product();
        testProduct.setId(1);
        testProduct.setName("Test Product");
        testProduct.setPrice(BigDecimal.valueOf(100.00));

        // Создаем тестовый отзыв
        testReview = new Review();
        testReview.setId(1L);
        testReview.setProduct(testProduct);
        testReview.setCustomer(testCustomer);
        testReview.setUserFirstName("John");
        testReview.setRating(5);
        testReview.setText("Great product!");
        testReview.setActive(false);
        testReview.setCreatedAt(LocalDateTime.now());

        // Создаем тестовые DTO
        testReviewDto = new ReviewDto(
                1L,
                "John",
                5,
                "Great product!",
                LocalDateTime.now(),
                true
        );

        testReviewModerationDto = new ReviewModerationDto(
                1L,
                "John",
                5,
                "Great product!",
                LocalDateTime.now(),
                "Test Product"
        );

        testProductResponseDto = new ProductResponseDto(
                1,
                "Test Product",
                0,
                BigDecimal.valueOf(100.00),
                BigDecimal.valueOf(100.00),
                1,
                "Test Category",
                "Test Brand",
                "test-logo.webp",
                24,
                2023,
                "Test Description",
                "test-image.webp",
                10
        );
    }

    @Test
    void prepareMyOrdersModel_ShouldReturnCorrectModel() {
        // Arrange
        List<Integer> productIds = List.of(1);
        List<Product> products = List.of(testProduct);
        when(customerRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testCustomer));
        when(orderProductRepository.findUniqueProductIdsByCustomerId(1)).thenReturn(productIds);
        when(productRepository.findAllById(productIds)).thenReturn(products);
        when(reviewRepository.findByProductAndCustomer(testProduct, testCustomer))
                .thenReturn(Optional.empty());

        // Act
        Map<String, Object> result = reviewService.prepareMyOrdersModel("test@example.com");

        // Assert
        assertNotNull(result);
        assertTrue(result.containsKey("allProducts"));
        assertTrue(result.containsKey("productsToReview"));
        assertTrue(result.containsKey("reviewedProducts"));
        assertTrue(result.containsKey("customer"));
        assertTrue(result.containsKey("userReviews"));

        @SuppressWarnings("unchecked")
        List<Product> productsToReview = (List<Product>) result.get("productsToReview");
        assertEquals(1, productsToReview.size());
        assertEquals(testProduct.getId(), productsToReview.getFirst().getId());

        verify(customerRepository).findByEmail("test@example.com");
        verify(orderProductRepository).findUniqueProductIdsByCustomerId(1);
        verify(productRepository).findAllById(productIds);
        verify(reviewRepository).findByProductAndCustomer(testProduct, testCustomer);
    }

    @Test
    void prepareMyOrdersModel_ShouldThrowExceptionWhenCustomerNotFound() {
        // Arrange
        when(customerRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                reviewService.prepareMyOrdersModel("nonexistent@example.com"));

        assertEquals(ApplicationConstants.CUSTOMER_NOT_FOUND, exception.getMessage());
        verify(customerRepository).findByEmail("nonexistent@example.com");
    }

    @Test
    void canCustomerReviewProduct_ShouldReturnTrueWhenCanReview() {
        // Arrange
        List<Integer> purchasedProductIds = List.of(1);
        when(customerRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testCustomer));
        when(orderProductRepository.findUniqueProductIdsByCustomerId(1)).thenReturn(purchasedProductIds);
        when(productRepository.findById(1)).thenReturn(Optional.of(testProduct));
        when(reviewRepository.findByProductAndCustomer(testProduct, testCustomer))
                .thenReturn(Optional.empty());

        // Act
        boolean result = reviewService.canCustomerReviewProduct("test@example.com", 1);

        // Assert
        assertTrue(result);
        verify(customerRepository).findByEmail("test@example.com");
        verify(orderProductRepository).findUniqueProductIdsByCustomerId(1);
        verify(productRepository).findById(1);
        verify(reviewRepository).findByProductAndCustomer(testProduct, testCustomer);
    }

    @Test
    void canCustomerReviewProduct_ShouldReturnFalseWhenAlreadyReviewed() {
        // Arrange
        List<Integer> purchasedProductIds = List.of(1);
        when(customerRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testCustomer));
        when(orderProductRepository.findUniqueProductIdsByCustomerId(1)).thenReturn(purchasedProductIds);
        when(productRepository.findById(1)).thenReturn(Optional.of(testProduct));
        when(reviewRepository.findByProductAndCustomer(testProduct, testCustomer))
                .thenReturn(Optional.of(testReview));

        // Act
        boolean result = reviewService.canCustomerReviewProduct("test@example.com", 1);

        // Assert
        assertFalse(result);
        verify(customerRepository).findByEmail("test@example.com");
        verify(orderProductRepository).findUniqueProductIdsByCustomerId(1);
        verify(productRepository).findById(1);
        verify(reviewRepository).findByProductAndCustomer(testProduct, testCustomer);
    }

    @Test
    void canCustomerReviewProduct_ShouldReturnFalseWhenNotPurchased() {
        // Arrange
        List<Integer> purchasedProductIds = List.of(2); // Different product ID
        when(customerRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testCustomer));
        when(orderProductRepository.findUniqueProductIdsByCustomerId(1)).thenReturn(purchasedProductIds);
        when(productRepository.findById(1)).thenReturn(Optional.of(testProduct));

        // Act
        boolean result = reviewService.canCustomerReviewProduct("test@example.com", 1);

        // Assert
        assertFalse(result);
        verify(customerRepository).findByEmail("test@example.com");
        verify(orderProductRepository).findUniqueProductIdsByCustomerId(1);
        verify(productRepository).findById(1);
        verify(reviewRepository).findByProductAndCustomer(testProduct, testCustomer);
    }

    @Test
    void getProductBasicInfo_ShouldReturnProductResponseDto() {
        // Arrange
        when(productRepository.findProductResponseDtoById(1)).thenReturn(testProductResponseDto);

        // Act
        ProductResponseDto result = reviewService.getProductBasicInfo(1);

        // Assert
        assertNotNull(result);
        assertEquals(testProductResponseDto.id(), result.id());
        assertEquals(testProductResponseDto.name(), result.name());
        assertEquals(testProductResponseDto.price(), result.price());

        verify(productRepository).findProductResponseDtoById(1);
    }

    @Test
    void createReview_ShouldCreateReviewSuccessfully() {
        // Arrange
        when(customerRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testCustomer));
        when(productRepository.findById(1)).thenReturn(Optional.of(testProduct));
        when(reviewRepository.save(any(Review.class))).thenReturn(testReview);

        // Act
        Review result = reviewService.createReview("test@example.com", 1, testReview);

        // Assert
        assertNotNull(result);
        assertEquals(testProduct, result.getProduct());
        assertEquals(testCustomer, result.getCustomer());
        assertEquals("John", result.getUserFirstName());
        assertFalse(result.isActive()); // Should be inactive until moderated

        verify(customerRepository).findByEmail("test@example.com");
        verify(productRepository).findById(1);
        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    void createReview_ShouldThrowExceptionWhenCustomerNotFound() {
        // Arrange
        when(customerRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                reviewService.createReview("nonexistent@example.com", 1, testReview));

        assertEquals(ApplicationConstants.CUSTOMER_NOT_FOUND, exception.getMessage());
        verify(customerRepository).findByEmail("nonexistent@example.com");
        verify(productRepository, never()).findById(anyInt());
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    void createReview_ShouldThrowExceptionWhenProductNotFound() {
        // Arrange
        when(customerRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testCustomer));
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                reviewService.createReview("test@example.com", 1, testReview));

        assertEquals(ApplicationConstants.PRODUCT_NOT_FOUND, exception.getMessage());
        verify(customerRepository).findByEmail("test@example.com");
        verify(productRepository).findById(1);
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    void getActiveReviewsByProductId_ShouldReturnReviewDtos() {
        // Arrange
        List<ReviewDto> expectedReviews = List.of(testReviewDto);
        when(reviewRepository.findActiveReviewDtosByProductId(1)).thenReturn(expectedReviews);

        // Act
        List<ReviewDto> result = reviewService.getActiveReviewsByProductId(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testReviewDto.id(), result.getFirst().id());
        assertEquals(testReviewDto.userFirstName(), result.getFirst().userFirstName());
        assertEquals(testReviewDto.rating(), result.getFirst().rating());
        assertEquals(testReviewDto.text(), result.getFirst().text());

        verify(reviewRepository).findActiveReviewDtosByProductId(1);
    }

    @Test
    void getAverageRatingByProductId_ShouldReturnAverageRating() {
        // Arrange
        Double expectedAverage = 4.5;
        when(reviewRepository.findAverageRatingByProductId(1)).thenReturn(expectedAverage);

        // Act
        Double result = reviewService.getAverageRatingByProductId(1);

        // Assert
        assertEquals(expectedAverage, result);
        verify(reviewRepository).findAverageRatingByProductId(1);
    }

    @Test
    void getActiveReviewCountByProductId_ShouldReturnCount() {
        // Arrange
        long expectedCount = 5L;
        when(reviewRepository.countByProductIdAndActiveTrue(1)).thenReturn(expectedCount);

        // Act
        long result = reviewService.getActiveReviewCountByProductId(1);

        // Assert
        assertEquals(expectedCount, result);
        verify(reviewRepository).countByProductIdAndActiveTrue(1);
    }

    @Test
    void getPendingReviews_ShouldReturnPageOfReviewModerationDtos() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<ReviewModerationDto> expectedPage = new PageImpl<>(List.of(testReviewModerationDto), pageable, 1);
        when(reviewRepository.findByActiveFalseOrderByCreatedAtDesc(pageable)).thenReturn(expectedPage);

        // Act
        Page<ReviewModerationDto> result = reviewService.getPendingReviews(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(testReviewModerationDto.reviewId(), result.getContent().getFirst().reviewId());
        assertEquals(testReviewModerationDto.userFirstName(), result.getContent().getFirst().userFirstName());
        assertEquals(testReviewModerationDto.productName(), result.getContent().getFirst().productName());

        verify(reviewRepository).findByActiveFalseOrderByCreatedAtDesc(pageable);
    }

    @Test
    void getPendingReviewsCount_ShouldReturnCount() {
        // Arrange
        long expectedCount = 3L;
        when(reviewRepository.countByActiveFalse()).thenReturn(expectedCount);

        // Act
        long result = reviewService.getPendingReviewsCount();

        // Assert
        assertEquals(expectedCount, result);
        verify(reviewRepository).countByActiveFalse();
    }

    @Test
    void approveReview_ShouldApproveReviewAndCreateNotification() {
        // Arrange
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(testReview));
        when(reviewRepository.save(any(Review.class))).thenReturn(testReview);

        // Act
        reviewService.approveReview(1L);

        // Assert
        assertTrue(testReview.isActive());
        verify(reviewRepository).findById(1L);
        verify(reviewRepository).save(testReview);
        verify(notificationService).createNotification(eq(1), contains("approved"));
    }

    @Test
    void approveReview_ShouldThrowExceptionWhenReviewNotFound() {
        // Arrange
        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                reviewService.approveReview(1L));

        assertEquals("Review not found", exception.getMessage());
        verify(reviewRepository).findById(1L);
        verify(reviewRepository, never()).save(any(Review.class));
        verify(notificationService, never()).createNotification(anyInt(), anyString());
    }

    @Test
    void deleteReview_ShouldDeleteReviewAndCreateNotification() {
        // Arrange
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(testReview));
        doNothing().when(reviewRepository).deleteById(1L);

        // Act
        reviewService.deleteReview(1L);

        // Assert
        verify(reviewRepository).findById(1L);
        verify(notificationService).createNotification(eq(1), contains("did not pass moderation"));
        verify(reviewRepository).deleteById(1L);
    }

    @Test
    void deleteReview_ShouldThrowExceptionWhenReviewNotFound() {
        // Arrange
        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                reviewService.deleteReview(1L));

        assertEquals("Review not found", exception.getMessage());
        verify(reviewRepository).findById(1L);
        verify(notificationService, never()).createNotification(anyInt(), anyString());
        verify(reviewRepository, never()).deleteById(anyLong());
    }
}
