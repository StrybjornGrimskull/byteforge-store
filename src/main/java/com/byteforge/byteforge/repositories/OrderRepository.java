package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findFirstByCustomerIdAndActiveTrueOrderByDateDesc(Integer customerId);
    List<Order> findByCustomerIdAndActiveTrue(Integer customerId);
}