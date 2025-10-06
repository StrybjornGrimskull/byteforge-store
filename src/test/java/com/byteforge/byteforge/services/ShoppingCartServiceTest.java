package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.response.ShoppingCartResponseDto;
import com.byteforge.byteforge.entities.Customer;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.entities.ShoppingCart;
import com.byteforge.byteforge.entities.StockQuantity;
import com.byteforge.byteforge.repositories.CustomerRepository;
import com.byteforge.byteforge.repositories.ProductRepository;
import com.byteforge.byteforge.repositories.ShoppingCartRepository;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private ShoppingCartService shoppingCartService;

    private Customer testCustomer;
    private Product testProduct;
    private ShoppingCart testCartItem;
    private ShoppingCartResponseDto testCartResponseDto;

    @BeforeEach
    void setUp() {
        // Создаем тестового клиента
        testCustomer = new Customer();
        testCustomer.setId(1);
        testCustomer.setEmail("test@example.com");

        // Создаем тестовое количество на складе
        StockQuantity testStockQuantity = new StockQuantity();
        testStockQuantity.setQuantity(10);

        // Создаем тестовый продукт
        testProduct = new Product();
        testProduct.setId(1);
        testProduct.setName("Test Product");
        testProduct.setPrice(BigDecimal.valueOf(100.00));
        testProduct.setImageUrl("test-image.webp");
        testProduct.setStockQuantity(testStockQuantity);

        // Создаем тестовый элемент корзины
        testCartItem = new ShoppingCart();
        testCartItem.setId(1);
        testCartItem.setCustomer(testCustomer);
        testCartItem.setProduct(testProduct);
        testCartItem.setQuantity(2);
        testCartItem.setAddedDate(LocalDateTime.now());

        // Создаем тестовый DTO ответа
        testCartResponseDto = new ShoppingCartResponseDto(
                "test-image.webp", // imageUrl
                1, // productId
                "Test Product", // productName
                BigDecimal.valueOf(100.00), // price
                2, // quantity
                10, // stockQuantity
                LocalDateTime.now() // addedDate
        );
    }

    @Test
    void getShoppingCartByUsername_ShouldReturnShoppingCartResponseDtos() {
        // Arrange
        List<ShoppingCartResponseDto> expectedList = List.of(testCartResponseDto);
        when(shoppingCartRepository.findCartItemsWithProductInfo("test@example.com"))
                .thenReturn(expectedList);

        // Act
        List<ShoppingCartResponseDto> result = shoppingCartService.getShoppingCartByUsername("test@example.com");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testCartResponseDto.imageUrl(), result.getFirst().imageUrl());
        assertEquals(testCartResponseDto.productId(), result.getFirst().productId());
        assertEquals(testCartResponseDto.productName(), result.getFirst().productName());
        assertEquals(testCartResponseDto.price(), result.getFirst().price());
        assertEquals(testCartResponseDto.quantity(), result.getFirst().quantity());
        assertEquals(testCartResponseDto.stockQuantity(), result.getFirst().stockQuantity());

        verify(shoppingCartRepository).findCartItemsWithProductInfo("test@example.com");
    }

    @Test
    void removeFromShoppingCart_ShouldDeleteCartItem() {
        // Arrange
        doNothing().when(shoppingCartRepository).deleteByProductIdAndCustomerEmail(1, "test@example.com");

        // Act
        shoppingCartService.removeFromShoppingCart(1, "test@example.com");

        // Assert
        verify(shoppingCartRepository).deleteByProductIdAndCustomerEmail(1, "test@example.com");
    }

    @Test
    void addToShoppingCart_ShouldAddNewCartItem() {
        // Arrange
        when(shoppingCartRepository.existsByProductIdAndCustomerEmail(1, "test@example.com"))
                .thenReturn(false);
        when(customerRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(testCustomer));
        when(productRepository.findById(1))
                .thenReturn(Optional.of(testProduct));
        when(shoppingCartRepository.save(any(ShoppingCart.class)))
                .thenReturn(testCartItem);

        // Act
        shoppingCartService.addToShoppingCart(1, "test@example.com");

        // Assert
        verify(shoppingCartRepository).existsByProductIdAndCustomerEmail(1, "test@example.com");
        verify(customerRepository).findByEmail("test@example.com");
        verify(productRepository).findById(1);
        verify(shoppingCartRepository).save(any(ShoppingCart.class));
    }

    @Test
    void addToShoppingCart_ShouldThrowExceptionWhenProductAlreadyInCart() {
        // Arrange
        when(shoppingCartRepository.existsByProductIdAndCustomerEmail(1, "test@example.com"))
                .thenReturn(true);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                shoppingCartService.addToShoppingCart(1, "test@example.com"));

        assertEquals("Товар уже находится в корзине", exception.getMessage());
        verify(shoppingCartRepository).existsByProductIdAndCustomerEmail(1, "test@example.com");
        verify(customerRepository, never()).findByEmail(anyString());
        verify(productRepository, never()).findById(anyInt());
        verify(shoppingCartRepository, never()).save(any(ShoppingCart.class));
    }

    @Test
    void addToShoppingCart_ShouldThrowExceptionWhenCustomerNotFound() {
        // Arrange
        when(shoppingCartRepository.existsByProductIdAndCustomerEmail(1, "test@example.com"))
                .thenReturn(false);
        when(customerRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                shoppingCartService.addToShoppingCart(1, "test@example.com"));

        assertEquals("Пользователь не найден", exception.getMessage());
        verify(shoppingCartRepository).existsByProductIdAndCustomerEmail(1, "test@example.com");
        verify(customerRepository).findByEmail("test@example.com");
        verify(productRepository, never()).findById(anyInt());
        verify(shoppingCartRepository, never()).save(any(ShoppingCart.class));
    }

    @Test
    void addToShoppingCart_ShouldThrowExceptionWhenProductNotFound() {
        // Arrange
        when(shoppingCartRepository.existsByProductIdAndCustomerEmail(1, "test@example.com"))
                .thenReturn(false);
        when(customerRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(testCustomer));
        when(productRepository.findById(1))
                .thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                shoppingCartService.addToShoppingCart(1, "test@example.com"));

        assertEquals("Товар не найден", exception.getMessage());
        verify(shoppingCartRepository).existsByProductIdAndCustomerEmail(1, "test@example.com");
        verify(customerRepository).findByEmail("test@example.com");
        verify(productRepository).findById(1);
        verify(shoppingCartRepository, never()).save(any(ShoppingCart.class));
    }

    @Test
    void isProductInShoppingCart_ShouldReturnTrueWhenProductExists() {
        // Arrange
        when(shoppingCartRepository.existsByProductIdAndCustomerEmail(1, "test@example.com"))
                .thenReturn(true);

        // Act
        boolean result = shoppingCartService.isProductInShoppingCart(1, "test@example.com");

        // Assert
        assertTrue(result);
        verify(shoppingCartRepository).existsByProductIdAndCustomerEmail(1, "test@example.com");
    }

    @Test
    void isProductInShoppingCart_ShouldReturnFalseWhenProductDoesNotExist() {
        // Arrange
        when(shoppingCartRepository.existsByProductIdAndCustomerEmail(1, "test@example.com"))
                .thenReturn(false);

        // Act
        boolean result = shoppingCartService.isProductInShoppingCart(1, "test@example.com");

        // Assert
        assertFalse(result);
        verify(shoppingCartRepository).existsByProductIdAndCustomerEmail(1, "test@example.com");
    }

    @Test
    void updateQuantity_ShouldUpdateCartItemQuantity() {
        // Arrange
        when(shoppingCartRepository.findByProductIdAndCustomerEmail(1, "test@example.com"))
                .thenReturn(Optional.of(testCartItem));
        when(productRepository.findById(1))
                .thenReturn(Optional.of(testProduct));
        when(shoppingCartRepository.save(any(ShoppingCart.class)))
                .thenReturn(testCartItem);

        // Act
        shoppingCartService.updateQuantity(1, "test@example.com", 5);

        // Assert
        verify(shoppingCartRepository).findByProductIdAndCustomerEmail(1, "test@example.com");
        verify(productRepository).findById(1);
        verify(shoppingCartRepository).save(any(ShoppingCart.class));
    }

    @Test
    void updateQuantity_ShouldThrowExceptionWhenCartItemNotFound() {
        // Arrange
        when(shoppingCartRepository.findByProductIdAndCustomerEmail(1, "test@example.com"))
                .thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                shoppingCartService.updateQuantity(1, "test@example.com", 5));

        assertEquals("Товар не найден в корзине", exception.getMessage());
        verify(shoppingCartRepository).findByProductIdAndCustomerEmail(1, "test@example.com");
        verify(productRepository, never()).findById(anyInt());
        verify(shoppingCartRepository, never()).save(any(ShoppingCart.class));
    }

    @Test
    void updateQuantity_ShouldThrowExceptionWhenProductNotFound() {
        // Arrange
        when(shoppingCartRepository.findByProductIdAndCustomerEmail(1, "test@example.com"))
                .thenReturn(Optional.of(testCartItem));
        when(productRepository.findById(1))
                .thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                shoppingCartService.updateQuantity(1, "test@example.com", 5));

        assertEquals("Товар не найден", exception.getMessage());
        verify(shoppingCartRepository).findByProductIdAndCustomerEmail(1, "test@example.com");
        verify(productRepository).findById(1);
        verify(shoppingCartRepository, never()).save(any(ShoppingCart.class));
    }

    @Test
    void updateQuantity_ShouldThrowExceptionWhenQuantityIsInvalid() {
        // Arrange
        when(shoppingCartRepository.findByProductIdAndCustomerEmail(1, "test@example.com"))
                .thenReturn(Optional.of(testCartItem));
        when(productRepository.findById(1))
                .thenReturn(Optional.of(testProduct));

        // Act & Assert - тест с количеством меньше 1
        RuntimeException exception1 = assertThrows(RuntimeException.class, () ->
                shoppingCartService.updateQuantity(1, "test@example.com", 0));

        assertEquals("Недопустимое количество", exception1.getMessage());

        // Act & Assert - тест с количеством больше доступного на складе
        RuntimeException exception2 = assertThrows(RuntimeException.class, () ->
                shoppingCartService.updateQuantity(1, "test@example.com", 15));

        assertEquals("Недопустимое количество", exception2.getMessage());

        verify(shoppingCartRepository, times(2)).findByProductIdAndCustomerEmail(1, "test@example.com");
        verify(productRepository, times(2)).findById(1);
        verify(shoppingCartRepository, never()).save(any(ShoppingCart.class));
    }
}
