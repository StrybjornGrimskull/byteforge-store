package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findFirstByCustomerIdAndActiveTrueOrderByDateDesc(Integer customerId);
    @Query("SELECT o FROM Order o JOIN FETCH o.orderProducts op JOIN FETCH op.product WHERE o.customer.id = :customerId AND o.active = true")
    List<Order> findByCustomerIdAndActiveTrue(@Param("customerId") Integer customerId);
    @Query("SELECT o FROM Order o JOIN FETCH o.orderProducts op JOIN FETCH op.product WHERE o.customer.id = :customerId AND o.active = false")
    List<Order> findByCustomerIdAndActiveFalse(@Param("customerId") Integer customerId);
}