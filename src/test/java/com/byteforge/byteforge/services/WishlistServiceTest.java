package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.response.WishlistItemResponseDto;
import com.byteforge.byteforge.entities.Customer;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.entities.WishlistItem;
import com.byteforge.byteforge.repositories.CustomerRepository;
import com.byteforge.byteforge.repositories.ProductRepository;
import com.byteforge.byteforge.repositories.WishlistItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WishlistServiceTest {

    @Mock
    private WishlistItemRepository wishlistItemRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private WishlistService wishlistService;

    private Customer testCustomer;
    private Product testProduct;
    private WishlistItem testWishlistItem;
    private WishlistItemResponseDto testWishlistItemDto;
    private Integer testProductId;
    private String testCustomerEmail;

    @BeforeEach
    void setUp() {
        testProductId = 1;
        testCustomerEmail = "test@example.com";
        String testProductName = "Test Product";
        BigDecimal testProductPrice = BigDecimal.valueOf(99.99);

        testCustomer = new Customer();
        testCustomer.setId(1);
        testCustomer.setEmail(testCustomerEmail);

        testProduct = new Product();
        testProduct.setId(testProductId);
        testProduct.setName(testProductName);
        testProduct.setPrice(testProductPrice);

        testWishlistItem = new WishlistItem();
        testWishlistItem.setId(1);
        testWishlistItem.setProduct(testProduct);
        testWishlistItem.setCustomer(testCustomer);
        testWishlistItem.setAddedDate(LocalDateTime.now());

        testWishlistItemDto = new WishlistItemResponseDto(
                "test-image.jpg",
                testProductId,
                testProductName,
                1,
                LocalDateTime.now()
        );
    }

    @Test
    void getWishlistByUsername_ShouldReturnWishlistItems() {
        // Arrange
        List<WishlistItemResponseDto> expectedItems = List.of(testWishlistItemDto);
        when(wishlistItemRepository.findWishlistItemsWithProductInfo(testCustomerEmail))
                .thenReturn(expectedItems);

        // Act
        List<WishlistItemResponseDto> result = wishlistService.getWishlistByUsername(testCustomerEmail);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testWishlistItemDto, result.getFirst());
        verify(wishlistItemRepository).findWishlistItemsWithProductInfo(testCustomerEmail);
    }

    @Test
    void getWishlistByUsername_ShouldReturnEmptyList_WhenNoItems() {
        // Arrange
        when(wishlistItemRepository.findWishlistItemsWithProductInfo(testCustomerEmail))
                .thenReturn(List.of());

        // Act
        List<WishlistItemResponseDto> result = wishlistService.getWishlistByUsername(testCustomerEmail);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(wishlistItemRepository).findWishlistItemsWithProductInfo(testCustomerEmail);
    }

    @Test
    void removeFromWishlist_ShouldDeleteWishlistItem() {
        // Act
        wishlistService.removeFromWishlist(testProductId, testCustomerEmail);

        // Assert
        verify(wishlistItemRepository).deleteByProductIdAndCustomerEmail(testProductId, testCustomerEmail);
    }

    @Test
    void addToWishlist_ShouldAddItem_WhenCustomerAndProductExist() {
        // Arrange
        when(customerRepository.findByEmail(testCustomerEmail)).thenReturn(Optional.of(testCustomer));
        when(productRepository.findById(testProductId)).thenReturn(Optional.of(testProduct));
        when(wishlistItemRepository.save(any(WishlistItem.class))).thenReturn(testWishlistItem);

        // Act
        wishlistService.addToWishlist(testProductId, testCustomerEmail);

        // Assert
        verify(customerRepository).findByEmail(testCustomerEmail);
        verify(productRepository).findById(testProductId);
        verify(wishlistItemRepository).save(any(WishlistItem.class));
    }

    @Test
    void addToWishlist_ShouldThrowRuntimeException_WhenCustomerNotFound() {
        // Arrange
        when(customerRepository.findByEmail(testCustomerEmail)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                wishlistService.addToWishlist(testProductId, testCustomerEmail));

        assertEquals("Пользователь не найден", exception.getMessage());
        verify(customerRepository).findByEmail(testCustomerEmail);
        verify(productRepository, never()).findById(any());
        verify(wishlistItemRepository, never()).save(any());
    }

    @Test
    void addToWishlist_ShouldThrowRuntimeException_WhenProductNotFound() {
        // Arrange
        when(customerRepository.findByEmail(testCustomerEmail)).thenReturn(Optional.of(testCustomer));
        when(productRepository.findById(testProductId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                wishlistService.addToWishlist(testProductId, testCustomerEmail));

        assertEquals("Товар не найден", exception.getMessage());
        verify(customerRepository).findByEmail(testCustomerEmail);
        verify(productRepository).findById(testProductId);
        verify(wishlistItemRepository, never()).save(any());
    }

    @Test
    void isProductInWishlist_ShouldReturnTrue_WhenProductExists() {
        // Arrange
        when(wishlistItemRepository.existsByProductIdAndCustomerEmail(testProductId, testCustomerEmail))
                .thenReturn(true);

        // Act
        boolean result = wishlistService.isProductInWishlist(testProductId, testCustomerEmail);

        // Assert
        assertTrue(result);
        verify(wishlistItemRepository).existsByProductIdAndCustomerEmail(testProductId, testCustomerEmail);
    }

    @Test
    void isProductInWishlist_ShouldReturnFalse_WhenProductNotExists() {
        // Arrange
        when(wishlistItemRepository.existsByProductIdAndCustomerEmail(testProductId, testCustomerEmail))
                .thenReturn(false);

        // Act
        boolean result = wishlistService.isProductInWishlist(testProductId, testCustomerEmail);

        // Assert
        assertFalse(result);
        verify(wishlistItemRepository).existsByProductIdAndCustomerEmail(testProductId, testCustomerEmail);
    }

    @Test
    void addToWishlist_ShouldSetCorrectWishlistItemProperties() {
        // Arrange
        when(customerRepository.findByEmail(testCustomerEmail)).thenReturn(Optional.of(testCustomer));
        when(productRepository.findById(testProductId)).thenReturn(Optional.of(testProduct));
        when(wishlistItemRepository.save(any(WishlistItem.class))).thenReturn(testWishlistItem);

        // Act
        wishlistService.addToWishlist(testProductId, testCustomerEmail);

        // Assert
        verify(wishlistItemRepository).save(argThat(wishlistItem -> {
            assertEquals(testProduct, wishlistItem.getProduct());
            assertEquals(testCustomer, wishlistItem.getCustomer());
            assertNotNull(wishlistItem.getAddedDate());
            return true;
        }));
    }

    @Test
    void getWishlistByUsername_ShouldHandleMultipleItems() {
        // Arrange
        WishlistItemResponseDto secondItem = new WishlistItemResponseDto(
                "second-image.jpg", 2, "Second Product", 1, LocalDateTime.now()
        );
        List<WishlistItemResponseDto> expectedItems = List.of(testWishlistItemDto, secondItem);
        when(wishlistItemRepository.findWishlistItemsWithProductInfo(testCustomerEmail))
                .thenReturn(expectedItems);

        // Act
        List<WishlistItemResponseDto> result = wishlistService.getWishlistByUsername(testCustomerEmail);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(testWishlistItemDto, result.getFirst());
        assertEquals(secondItem, result.get(1));
        verify(wishlistItemRepository).findWishlistItemsWithProductInfo(testCustomerEmail);
    }

    @Test
    void removeFromWishlist_ShouldHandleNonExistentItem() {
        // Act & Assert
        assertDoesNotThrow(() -> wishlistService.removeFromWishlist(testProductId, testCustomerEmail));
        verify(wishlistItemRepository).deleteByProductIdAndCustomerEmail(testProductId, testCustomerEmail);
    }
}
