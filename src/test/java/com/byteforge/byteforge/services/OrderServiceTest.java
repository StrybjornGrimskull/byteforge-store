package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.response.OrderResponseDto;
import com.byteforge.byteforge.entities.Customer;
import com.byteforge.byteforge.entities.Order;
import com.byteforge.byteforge.entities.OrderProduct;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.repositories.CustomerRepository;
import com.byteforge.byteforge.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCompletedOrdersForUser_returnsCompletedOrders() {
        // Arrange
        String email = "test@example.com";
        Customer customer = new Customer();
        customer.setId(1);
        customer.setEmail(email);

        Order completedOrder = new Order();
        completedOrder.setId(1L);
        completedOrder.setActive(false);
        completedOrder.setTotalPrice(BigDecimal.valueOf(200));
        completedOrder.setDate(LocalDateTime.now());
        completedOrder.setFirstName("John");
        completedOrder.setLastName("Doe");
        completedOrder.setCity("TestCity");
        completedOrder.setAddress("TestAddress");
        completedOrder.setEmail(email);
        completedOrder.setPhoneNumber("1234567890");
        completedOrder.setPostIndex(12345);
        completedOrder.setCustomer(customer);

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setId(1L);
        orderProduct.setQuantity(2);

        Product product = new Product();
        product.setName("Test Product");
        product.setImageUrl("test.jpg");
        product.setPrice(BigDecimal.valueOf(100));
        orderProduct.setProduct(product);

        completedOrder.setOrderProducts(List.of(orderProduct));


        when(customerRepository.findByEmail(email)).thenReturn(Optional.of(customer));
        when(orderRepository.findByCustomerIdAndActiveFalse(customer.getId()))
                .thenReturn(List.of(completedOrder));

        // Act
        List<OrderResponseDto> result = orderService.getCompletedOrdersForUser(email);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(completedOrder.getId(), result.get(0).id());
    }
} 