//package com.byteforge.byteforge.services;
//
//import com.byteforge.byteforge.dto.request.OrderRequestDto;
//import com.byteforge.byteforge.dto.response.OrderResponseDto;
//import com.byteforge.byteforge.entities.*;
//import com.byteforge.byteforge.repositories.*;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class OrderServiceImpl implements OrderService {
//
//    private final OrderRepository orderRepository;
//    private final CustomerRepository customerRepository;
//    private final ShoppingCartRepository shoppingCartRepository;
//    private final ProductRepository productRepository;
//
//    @Override
//    @Transactional
//    public OrderResponseDto createOrder(String email, OrderRequestDto orderDto) {
//        Customer customer = customerRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("Customer not found"));
//
//        List<ShoppingCart> cartItems = shoppingCartRepository.findAllProductCustomerByEmail(email);
//
//        if (cartItems.isEmpty()) {
//            throw new RuntimeException("Shopping cart is empty");
//        }
//
//        Order order = new Order();
//        order.setCustomer(customer);
//        order.setFirstName(orderDto.getFirstName());
//        order.setLastName(orderDto.getLastName());
//        order.setEmail(orderDto.getEmail());
//        order.setPhoneNumber(orderDto.getPhoneNumber());
//        order.setCity(orderDto.getCity());
//        order.setAddress(orderDto.getAddress());
//        order.setPostIndex(orderDto.getPostIndex());
//        order.setDate(LocalDateTime.now());
//
//        List<OrderProduct> orderProducts = cartItems.stream().map(cartItem -> {
//            OrderProduct orderProduct = new OrderProduct();
//            orderProduct.setOrder(order);
//            orderProduct.setProduct(cartItem.getProduct());
//            orderProduct.setQuantity(cartItem.getQuantity());
//            return orderProduct;
//        }).collect(Collectors.toList());
//
//        order.setOrderProducts(orderProducts);
//
//        double totalPrice = cartItems.stream()
//                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
//                .sum();
//        order.setTotalPrice(totalPrice);
//
//        Order savedOrder = orderRepository.save(order);
//        shoppingCartRepository.deleteAll(cartItems);
//
//        return OrderResponseDto.fromEntity(savedOrder);
//    }
//
//    @Override
//    public Optional<OrderResponseDto> getOrderByDateAndEmail(LocalDateTime date, String customerEmail) {
//        return orderRepository.findByDateAndCustomerEmail(date, customerEmail)
//                .map(OrderResponseDto::fromEntity);
//    }
//
//    @Override
//    public List<OrderResponseDto> getOrdersByCustomerEmail(String email) {
//        return orderRepository.findByCustomerEmail(email).stream()
//                .map(OrderResponseDto::fromEntity)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public Optional<OrderResponseDto> getOrderById(Long id) {
//        return orderRepository.findById(id)
//                .map(OrderResponseDto::fromEntity);
//    }
//}