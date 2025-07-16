package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.request.OrderRequestDto;
import com.byteforge.byteforge.dto.response.ActiveOrderDto;
import com.byteforge.byteforge.dto.response.OrderResponseDto;
import com.byteforge.byteforge.entities.Customer;
import com.byteforge.byteforge.entities.Order;
import com.byteforge.byteforge.entities.OrderProduct;
import com.byteforge.byteforge.entities.ShoppingCart;
import com.byteforge.byteforge.repositories.CustomerRepository;
import com.byteforge.byteforge.repositories.OrderRepository;
import com.byteforge.byteforge.repositories.ProductRepository;
import com.byteforge.byteforge.repositories.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService{

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    private final EmailService emailService;

    @Transactional
    public OrderResponseDto createOrder(String email, OrderRequestDto orderDto) {
        // 1. Получаем покупателя
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // 2. Получаем товары из корзины
        List<ShoppingCart> cartItems = shoppingCartRepository.findAllProductCustomerByEmail(email);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Shopping cart is empty");
        }

        // 3. Создаем заказ
        Order order = new Order();
        order.setCustomer(customer);
        order.setFirstName(orderDto.getFirstName());
        order.setLastName(orderDto.getLastName());
        order.setEmail(orderDto.getEmail());
        order.setPhoneNumber(orderDto.getPhoneNumber());
        order.setCity(orderDto.getCity());
        order.setAddress(orderDto.getAddress());
        order.setPostIndex(orderDto.getPostIndex());
        order.setDate(LocalDateTime.now());

        // 4. Создаем продукты заказа
        List<OrderProduct> orderProducts = cartItems.stream().map(cartItem -> {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(cartItem.getProduct());
            orderProduct.setQuantity(cartItem.getQuantity());
            return orderProduct;
        }).collect(Collectors.toList());

        order.setOrderProducts(orderProducts);

        // 5. Рассчитываем общую стоимость
        BigDecimal totalPrice = cartItems.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalPrice(totalPrice);

        // 6. Сохраняем заказ и очищаем корзину
        Order savedOrder = orderRepository.save(order);
        shoppingCartRepository.deleteAll(cartItems);

        // Отправка письма с подтверждением заказа
        List<String> productNames = orderProducts.stream()
                .map(op -> op.getProduct().getName())
                .collect(Collectors.toList());
        emailService.sendOrderConfirmationEmail(
                order.getEmail(),
                order.getFirstName(),
                savedOrder.getId(),
                productNames,
                totalPrice.doubleValue()
        );

        // 7. Возвращаем DTO
        return OrderResponseDto.fromEntity(savedOrder);
    }

    @Transactional(readOnly = true)
    public List<ActiveOrderDto> getActiveOrdersForUser(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        List<Order> orders = orderRepository.findByCustomerIdAndActiveTrue(customer.getId());
        return orders.stream().map(ActiveOrderDto::fromEntity).toList();
    }
}