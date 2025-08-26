package com.byteforge.byteforge.services;

import com.byteforge.byteforge.constants.ApplicationConstants;
import com.byteforge.byteforge.dto.ProductDto;
import com.byteforge.byteforge.dto.request.OrderRequestDto;
import com.byteforge.byteforge.dto.response.ActiveOrderDto;
import com.byteforge.byteforge.dto.response.OrderProductResponseDto;
import com.byteforge.byteforge.dto.response.OrderResponseDto;
import com.byteforge.byteforge.entities.*;
import com.byteforge.byteforge.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    private final StockQuantityRepository stockQuantityRepository;
    private final EmailService emailService;

    @Transactional
    public OrderResponseDto createOrder(String email, OrderRequestDto orderDto) {
        // 1. Получаем покупателя
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(ApplicationConstants.CUSTOMER_NOT_FOUND));

        // 2. Получаем товары из корзины
        List<ShoppingCart> cartItems = shoppingCartRepository.findAllProductCustomerByEmail(email);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Shopping cart is empty");
        }

        // 3. Создаем заказ
        Order order = new Order();
        order.setCustomer(customer);
        order.setFirstName(orderDto.firstName());
        order.setLastName(orderDto.lastName());
        order.setEmail(orderDto.email());
        order.setPhoneNumber(orderDto.phoneNumber());
        order.setCity(orderDto.city());
        order.setAddress(orderDto.address());
        order.setPostIndex(Integer.parseInt(orderDto.postIndex()));
        order.setDate(LocalDateTime.now());

        // 4. Создаем продукты заказа
        List<OrderProduct> orderProducts = cartItems.stream().map(cartItem -> {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(cartItem.getProduct());
            orderProduct.setQuantity(cartItem.getQuantity());
            return orderProduct;
        }).toList();

        order.setOrderProducts(orderProducts);

        // 5. Рассчитываем общую стоимость
        BigDecimal totalPrice = cartItems.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalPrice(totalPrice);

        // 6. Сохраняем заказ и очищаем корзину
        Order savedOrder = orderRepository.save(order);

        // 7. Уменьшаем количество на складе для каждого продукта
        for (OrderProduct op : savedOrder.getOrderProducts()) {
            Product product = op.getProduct();
            StockQuantity stock = product.getStockQuantity();
            int newQty = stock.getQuantity() - op.getQuantity();
            stock.setQuantity(newQty);
            stockQuantityRepository.save(stock);
        }
        shoppingCartRepository.deleteAll(cartItems);

        // 8. Отправка письма с подтверждением заказа
        List<String> productNames = orderProducts.stream()
                .map(op -> op.getProduct().getName())
                .toList();
        emailService.sendOrderConfirmationEmail(
                order.getEmail(),
                order.getFirstName(),
                savedOrder.getId(),
                productNames,
                totalPrice.doubleValue()
        );

        // 9. Возвращаем DTO
        return new OrderResponseDto(
                savedOrder.getId(),
                savedOrder.getTotalPrice(),
                savedOrder.getDate(),
                savedOrder.getFirstName(),
                savedOrder.getLastName(),
                savedOrder.getCity(),
                savedOrder.getAddress(),
                savedOrder.getEmail(),
                savedOrder.getPhoneNumber(),
                savedOrder.getPostIndex(),
                savedOrder.getOrderProducts().stream()
                        .map(op -> new OrderProductResponseDto(
                                op.getOrder().getId(),
                                op.getQuantity(),
                                new ProductDto(
                                        op.getProduct().getName(),
                                        op.getProduct().getPrice(),
                                        op.getProduct().getImageUrl()
                                )
                        ))
                        .toList(),
                savedOrder.getCustomer().getId()
        );
    }

    @Transactional(readOnly = true)
    public List<ActiveOrderDto> getActiveOrdersForUser(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(ApplicationConstants.CUSTOMER_NOT_FOUND));
        List<Order> orders = orderRepository.findByCustomerIdAndActiveTrue(customer.getId());
        return orders.stream().map(order -> new ActiveOrderDto(
                order.getId(),
                order.getTotalPrice(),
                order.getDate()
        )).toList();
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDto> getCompletedOrdersForUser(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(ApplicationConstants.CUSTOMER_NOT_FOUND));
        List<Order> orders = orderRepository.findByCustomerIdAndActiveFalse(customer.getId());
        return orders.stream().map(order -> new OrderResponseDto(
                order.getId(),
                order.getTotalPrice(),
                order.getDate(),
                order.getFirstName(),
                order.getLastName(),
                order.getCity(),
                order.getAddress(),
                order.getEmail(),
                order.getPhoneNumber(),
                order.getPostIndex(),
                order.getOrderProducts().stream()
                        .map(op -> new OrderProductResponseDto(
                                op.getOrder().getId(),
                                op.getQuantity(),
                                new ProductDto(
                                        op.getProduct().getName(),
                                        op.getProduct().getPrice(),
                                        op.getProduct().getImageUrl()
                                )
                        ))
                        .toList(),
                order.getCustomer().getId()
        )).toList();
    }

    @Transactional(readOnly = true)
    public Page<OrderResponseDto> getAllActiveOrders(Pageable pageable) {
        return orderRepository.findAllByActiveTrue(pageable)
                .map(order -> new OrderResponseDto(
                        order.getId(),
                        order.getTotalPrice(),
                        order.getDate(),
                        order.getFirstName(),
                        order.getLastName(),
                        order.getCity(),
                        order.getAddress(),
                        order.getEmail(),
                        order.getPhoneNumber(),
                        order.getPostIndex(),
                        order.getOrderProducts().stream()
                                .map(op -> new OrderProductResponseDto(
                                        op.getOrder().getId(),
                                        op.getQuantity(),
                                        new ProductDto(
                                                op.getProduct().getName(),
                                                op.getProduct().getPrice(),
                                                op.getProduct().getImageUrl()
                                        )
                                ))
                                .toList(),
                        order.getCustomer().getId()
                ));
    }

    @Transactional(readOnly = true)
    public Page<OrderResponseDto> getAllArchivedOrders(Pageable pageable) {
        return orderRepository.findAllByActiveFalse(pageable)
                .map(order -> new OrderResponseDto(
                        order.getId(),
                        order.getTotalPrice(),
                        order.getDate(),
                        order.getFirstName(),
                        order.getLastName(),
                        order.getCity(),
                        order.getAddress(),
                        order.getEmail(),
                        order.getPhoneNumber(),
                        order.getPostIndex(),
                        order.getOrderProducts().stream()
                                .map(op -> new OrderProductResponseDto(
                                        op.getOrder().getId(),
                                        op.getQuantity(),
                                        new ProductDto(
                                                op.getProduct().getName(),
                                                op.getProduct().getPrice(),
                                                op.getProduct().getImageUrl()
                                        )
                                ))
                                .toList(),
                        order.getCustomer().getId()
                ));
    }
    
    @Transactional(readOnly = true)
    public OrderResponseDto getActiveOrderById(Long orderId) {
        return orderRepository.findByIdAndActiveTrue(orderId)
                .map(order -> new OrderResponseDto(
                        order.getId(),
                        order.getTotalPrice(),
                        order.getDate(),
                        order.getFirstName(),
                        order.getLastName(),
                        order.getCity(),
                        order.getAddress(),
                        order.getEmail(),
                        order.getPhoneNumber(),
                        order.getPostIndex(),
                        order.getOrderProducts().stream()
                                .map(op -> new OrderProductResponseDto(
                                        op.getOrder().getId(),
                                        op.getQuantity(),
                                        new ProductDto(
                                                op.getProduct().getName(),
                                                op.getProduct().getPrice(),
                                                op.getProduct().getImageUrl()
                                        )
                                ))
                                .toList(),
                        order.getCustomer().getId()
                ))
                .orElseThrow(() -> new RuntimeException(ApplicationConstants.ORDER_NOT_FOUND));
    }
    
    @Transactional(readOnly = true)
    public OrderResponseDto getArchivedOrderById(Long orderId) {
        return orderRepository.findByIdAndActiveFalse(orderId)
                .map(order -> new OrderResponseDto(
                        order.getId(),
                        order.getTotalPrice(),
                        order.getDate(),
                        order.getFirstName(),
                        order.getLastName(),
                        order.getCity(),
                        order.getAddress(),
                        order.getEmail(),
                        order.getPhoneNumber(),
                        order.getPostIndex(),
                        order.getOrderProducts().stream()
                                .map(op -> new OrderProductResponseDto(
                                        op.getOrder().getId(),
                                        op.getQuantity(),
                                        new ProductDto(
                                                op.getProduct().getName(),
                                                op.getProduct().getPrice(),
                                                op.getProduct().getImageUrl()
                                        )
                                ))
                                .toList(),
                        order.getCustomer().getId()
                ))
                .orElseThrow(() -> new RuntimeException(ApplicationConstants.ORDER_NOT_FOUND));
    }

    @Transactional
    public void completeOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException(ApplicationConstants.ORDER_NOT_FOUND));
        order.setActive(false);
        orderRepository.save(order);
    }
}