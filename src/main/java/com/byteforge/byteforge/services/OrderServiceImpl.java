package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.request.OrderRequestDto;
import com.byteforge.byteforge.dto.response.OrderResponseDto;
import com.byteforge.byteforge.entities.*;
import com.byteforge.byteforge.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;

    @Override
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
        double totalPrice = cartItems.stream()
                .mapToDouble(item -> {
                    BigDecimal productPrice = item.getProduct().getPrice();
                    int quantity = item.getQuantity();
                    return productPrice.multiply(BigDecimal.valueOf(quantity)).doubleValue();
                })
                .sum();
        order.setTotalPrice(totalPrice);

        // 6. Сохраняем заказ и очищаем корзину
        Order savedOrder = orderRepository.save(order);
        shoppingCartRepository.deleteAll(cartItems);

        // 7. Возвращаем DTO
        return OrderResponseDto.fromEntity(savedOrder);
    }
}