package com.byteforge.byteforge.services;

import com.byteforge.byteforge.constants.ApplicationConstants;
import com.byteforge.byteforge.dto.request.OrderRequestDto;
import com.byteforge.byteforge.dto.response.ActiveOrderDto;
import com.byteforge.byteforge.dto.response.OrderResponseDto;
import com.byteforge.byteforge.entities.*;
import com.byteforge.byteforge.repositories.CustomerRepository;
import com.byteforge.byteforge.repositories.OrderRepository;
import com.byteforge.byteforge.repositories.ShoppingCartRepository;
import com.byteforge.byteforge.repositories.StockQuantityRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private StockQuantityRepository stockQuantityRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private OrderService orderService;

    private Customer testCustomer;
    private Product testProduct;
    private ShoppingCart testCartItem;
    private Order testOrder;
    private OrderRequestDto testOrderRequest;

    @BeforeEach
    void setUp() {
        // Создаем тестового клиента
        testCustomer = new Customer();
        testCustomer.setId(1);
        testCustomer.setEmail("test@example.com");

        Profile profile = new Profile();
        profile.setFirstName("John");
        profile.setLastName("Doe");
        testCustomer.setProfile(profile);

        // Создаем тестовый продукт
        testProduct = new Product();
        testProduct.setId(1);
        testProduct.setName("Test Product");
        testProduct.setPrice(BigDecimal.valueOf(100.00));

        StockQuantity stockQuantity = new StockQuantity();
        stockQuantity.setQuantity(10);
        testProduct.setStockQuantity(stockQuantity);

        // Создаем тестовый элемент корзины
        testCartItem = new ShoppingCart();
        testCartItem.setProduct(testProduct);
        testCartItem.setCustomer(testCustomer);
        testCartItem.setQuantity(2);

        // Создаем тестовый заказ
        testOrder = new Order();
        testOrder.setId(1L);
        testOrder.setCustomer(testCustomer);
        testOrder.setFirstName("John");
        testOrder.setLastName("Doe");
        testOrder.setEmail("test@example.com");
        testOrder.setPhoneNumber("123456789");
        testOrder.setCity("Test City");
        testOrder.setAddress("Test Address");
        testOrder.setPostIndex(12345);
        testOrder.setDate(LocalDateTime.now());
        testOrder.setTotalPrice(BigDecimal.valueOf(200.00));
        testOrder.setActive(true);
        
        // Создаем OrderProduct для тестового заказа
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setId(1L);
        orderProduct.setOrder(testOrder);
        orderProduct.setProduct(testProduct);
        orderProduct.setQuantity(2);
        
        testOrder.setOrderProducts(List.of(orderProduct));

        // Создаем тестовый запрос заказа
        testOrderRequest = new OrderRequestDto(
                "John",
                "Doe",
                "test@example.com",
                "123456789",
                "Test City",
                "Test Address",
                "12345"
        );
    }

    @Test
    void createOrder_ShouldCreateOrderSuccessfully() {
        // Arrange
        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.of(testCustomer));
        when(shoppingCartRepository.findAllProductCustomerByEmail(anyString())).thenReturn(List.of(testCartItem));
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);
        when(stockQuantityRepository.save(any(StockQuantity.class))).thenReturn(testProduct.getStockQuantity());

        // Act
        OrderResponseDto result = orderService.createOrder("test@example.com", testOrderRequest);

        // Assert
        assertNotNull(result);
        assertEquals(testOrder.getId(), result.id());
        assertEquals(testOrder.getTotalPrice(), result.totalPrice());
        assertEquals(testOrder.getFirstName(), result.firstName());
        assertEquals(testOrder.getLastName(), result.lastName());
        assertEquals(testOrder.getEmail(), result.email());
        assertEquals(testOrder.getPhoneNumber(), result.phoneNumber());
        assertEquals(testOrder.getCity(), result.city());
        assertEquals(testOrder.getAddress(), result.address());
        assertEquals(testOrder.getPostIndex(), result.postIndex());
        assertEquals(testCustomer.getId(), result.customer());

        verify(customerRepository).findByEmail("test@example.com");
        verify(shoppingCartRepository).findAllProductCustomerByEmail("test@example.com");
        verify(orderRepository).save(any(Order.class));
        verify(stockQuantityRepository).save(any(StockQuantity.class));
        verify(shoppingCartRepository).deleteAll(List.of(testCartItem));
        verify(emailService).sendOrderConfirmationEmail(anyString(), anyString(), anyLong(), anyList(), any(BigDecimal.class));
    }

    @Test
    void createOrder_ShouldThrowExceptionWhenCustomerNotFound() {
        // Arrange
        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
                orderService.createOrder("nonexistent@example.com", testOrderRequest));
        
        assertEquals(ApplicationConstants.CUSTOMER_NOT_FOUND, exception.getMessage());
        verify(customerRepository).findByEmail("nonexistent@example.com");
        verify(shoppingCartRepository, never()).findAllProductCustomerByEmail(anyString());
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void createOrder_ShouldThrowExceptionWhenShoppingCartIsEmpty() {
        // Arrange
        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.of(testCustomer));
        when(shoppingCartRepository.findAllProductCustomerByEmail(anyString())).thenReturn(List.of());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
                orderService.createOrder("test@example.com", testOrderRequest));
        
        assertEquals("Shopping cart is empty", exception.getMessage());
        verify(customerRepository).findByEmail("test@example.com");
        verify(shoppingCartRepository).findAllProductCustomerByEmail("test@example.com");
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void getActiveOrdersForUser_ShouldReturnActiveOrders() {
        // Arrange
        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.of(testCustomer));
        when(orderRepository.findByCustomerIdAndActiveTrue(anyInt())).thenReturn(List.of(testOrder));

        // Act
        List<ActiveOrderDto> result = orderService.getActiveOrdersForUser("test@example.com");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testOrder.getId(), result.getFirst().id());
        assertEquals(testOrder.getTotalPrice(), result.getFirst().totalPrice());
        assertEquals(testOrder.getDate(), result.getFirst().date());

        verify(customerRepository).findByEmail("test@example.com");
        verify(orderRepository).findByCustomerIdAndActiveTrue(1);
    }

    @Test
    void getActiveOrdersForUser_ShouldThrowExceptionWhenCustomerNotFound() {
        // Arrange
        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
                orderService.getActiveOrdersForUser("nonexistent@example.com"));
        
        assertEquals(ApplicationConstants.CUSTOMER_NOT_FOUND, exception.getMessage());
        verify(customerRepository).findByEmail("nonexistent@example.com");
        verify(orderRepository, never()).findByCustomerIdAndActiveTrue(anyInt());
    }

    @Test
    void getCompletedOrdersForUser_ShouldReturnCompletedOrders() {
        // Arrange
        testOrder.setActive(false);
        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.of(testCustomer));
        when(orderRepository.findByCustomerIdAndActiveFalse(anyInt())).thenReturn(List.of(testOrder));

        // Act
        List<OrderResponseDto> result = orderService.getCompletedOrdersForUser("test@example.com");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testOrder.getId(), result.getFirst().id());
        assertEquals(testOrder.getTotalPrice(), result.getFirst().totalPrice());
        assertEquals(testOrder.getFirstName(), result.getFirst().firstName());
        assertEquals(testOrder.getLastName(), result.getFirst().lastName());
        assertEquals(testOrder.getEmail(), result.getFirst().email());
        assertEquals(testOrder.getPhoneNumber(), result.getFirst().phoneNumber());
        assertEquals(testOrder.getCity(), result.getFirst().city());
        assertEquals(testOrder.getAddress(), result.getFirst().address());
        assertEquals(testOrder.getPostIndex(), result.getFirst().postIndex());
        assertEquals(testCustomer.getId(), result.getFirst().customer());

        verify(customerRepository).findByEmail("test@example.com");
        verify(orderRepository).findByCustomerIdAndActiveFalse(1);
    }

    @Test
    void getAllActiveOrders_ShouldReturnPageOfActiveOrders() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<Order> orderPage = new PageImpl<>(List.of(testOrder), pageable, 1);
        when(orderRepository.findAllByActiveTrue(any(Pageable.class))).thenReturn(orderPage);

        // Act
        Page<OrderResponseDto> result = orderService.getAllActiveOrders(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(testOrder.getId(), result.getContent().getFirst().id());
        assertEquals(testOrder.getTotalPrice(), result.getContent().getFirst().totalPrice());
        assertEquals(testOrder.getFirstName(), result.getContent().getFirst().firstName());
        assertEquals(testOrder.getLastName(), result.getContent().getFirst().lastName());
        assertEquals(testOrder.getEmail(), result.getContent().getFirst().email());
        assertEquals(testOrder.getPhoneNumber(), result.getContent().getFirst().phoneNumber());
        assertEquals(testOrder.getCity(), result.getContent().getFirst().city());
        assertEquals(testOrder.getAddress(), result.getContent().getFirst().address());
        assertEquals(testOrder.getPostIndex(), result.getContent().getFirst().postIndex());
        assertEquals(testCustomer.getId(), result.getContent().getFirst().customer());

        verify(orderRepository).findAllByActiveTrue(pageable);
    }

    @Test
    void getAllArchivedOrders_ShouldReturnPageOfArchivedOrders() {
        // Arrange
        testOrder.setActive(false);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Order> orderPage = new PageImpl<>(List.of(testOrder), pageable, 1);
        when(orderRepository.findAllByActiveFalse(any(Pageable.class))).thenReturn(orderPage);

        // Act
        Page<OrderResponseDto> result = orderService.getAllArchivedOrders(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(testOrder.getId(), result.getContent().getFirst().id());
        assertEquals(testOrder.getTotalPrice(), result.getContent().getFirst().totalPrice());
        assertEquals(testOrder.getFirstName(), result.getContent().getFirst().firstName());
        assertEquals(testOrder.getLastName(), result.getContent().getFirst().lastName());
        assertEquals(testOrder.getEmail(), result.getContent().getFirst().email());
        assertEquals(testOrder.getPhoneNumber(), result.getContent().getFirst().phoneNumber());
        assertEquals(testOrder.getCity(), result.getContent().getFirst().city());
        assertEquals(testOrder.getAddress(), result.getContent().getFirst().address());
        assertEquals(testOrder.getPostIndex(), result.getContent().getFirst().postIndex());
        assertEquals(testCustomer.getId(), result.getContent().getFirst().customer());

        verify(orderRepository).findAllByActiveFalse(pageable);
    }

    @Test
    void getActiveOrderById_ShouldReturnActiveOrder() {
        // Arrange
        when(orderRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.of(testOrder));

        // Act
        OrderResponseDto result = orderService.getActiveOrderById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(testOrder.getId(), result.id());
        assertEquals(testOrder.getTotalPrice(), result.totalPrice());
        assertEquals(testOrder.getFirstName(), result.firstName());
        assertEquals(testOrder.getLastName(), result.lastName());
        assertEquals(testOrder.getEmail(), result.email());
        assertEquals(testOrder.getPhoneNumber(), result.phoneNumber());
        assertEquals(testOrder.getCity(), result.city());
        assertEquals(testOrder.getAddress(), result.address());
        assertEquals(testOrder.getPostIndex(), result.postIndex());
        assertEquals(testCustomer.getId(), result.customer());

        verify(orderRepository).findByIdAndActiveTrue(1L);
    }

    @Test
    void getActiveOrderById_ShouldThrowExceptionWhenOrderNotFound() {
        // Arrange
        when(orderRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
                orderService.getActiveOrderById(999L));
        
        assertEquals(ApplicationConstants.ORDER_NOT_FOUND, exception.getMessage());
        verify(orderRepository).findByIdAndActiveTrue(999L);
    }

    @Test
    void getArchivedOrderById_ShouldReturnArchivedOrder() {
        // Arrange
        testOrder.setActive(false);
        when(orderRepository.findByIdAndActiveFalse(anyLong())).thenReturn(Optional.of(testOrder));

        // Act
        OrderResponseDto result = orderService.getArchivedOrderById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(testOrder.getId(), result.id());
        assertEquals(testOrder.getTotalPrice(), result.totalPrice());
        assertEquals(testOrder.getFirstName(), result.firstName());
        assertEquals(testOrder.getLastName(), result.lastName());
        assertEquals(testOrder.getEmail(), result.email());
        assertEquals(testOrder.getPhoneNumber(), result.phoneNumber());
        assertEquals(testOrder.getCity(), result.city());
        assertEquals(testOrder.getAddress(), result.address());
        assertEquals(testOrder.getPostIndex(), result.postIndex());
        assertEquals(testCustomer.getId(), result.customer());

        verify(orderRepository).findByIdAndActiveFalse(1L);
    }

    @Test
    void getArchivedOrderById_ShouldThrowExceptionWhenOrderNotFound() {
        // Arrange
        when(orderRepository.findByIdAndActiveFalse(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
                orderService.getArchivedOrderById(999L));
        
        assertEquals(ApplicationConstants.ORDER_NOT_FOUND, exception.getMessage());
        verify(orderRepository).findByIdAndActiveFalse(999L);
    }

    @Test
    void completeOrder_ShouldCompleteOrderSuccessfully() {
        // Arrange
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(testOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);

        // Act
        orderService.completeOrder(1L);

        // Assert
        assertFalse(testOrder.isActive());
        verify(orderRepository).findById(1L);
        verify(orderRepository).save(testOrder);
    }

    @Test
    void completeOrder_ShouldThrowExceptionWhenOrderNotFound() {
        // Arrange
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
                orderService.completeOrder(999L));
        
        assertEquals(ApplicationConstants.ORDER_NOT_FOUND, exception.getMessage());
        verify(orderRepository).findById(999L);
        verify(orderRepository, never()).save(any(Order.class));
    }
}
