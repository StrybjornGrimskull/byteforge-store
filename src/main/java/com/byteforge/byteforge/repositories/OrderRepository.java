package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByIdAndCustomerEmail(Long orderId, String email);

    Optional<Order> findByDateAndCustomerEmail(LocalDateTime date, String customerEmail);
}