package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findFirstByCustomerIdAndActiveTrueOrderByDateDesc(Integer customerId);

    @EntityGraph(attributePaths = {"orderProducts", "orderProducts.product"})
    List<Order> findByCustomerIdAndActiveTrue(Integer customerId);

    @EntityGraph(attributePaths = {"orderProducts", "orderProducts.product"})
    List<Order> findByCustomerIdAndActiveFalse(Integer customerId);

    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderProducts op LEFT JOIN FETCH op.product WHERE o.active = true")
    Page<Order> findAllByActiveTrue(Pageable pageable);

    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderProducts op LEFT JOIN FETCH op.product WHERE o.active = false")
    Page<Order> findAllByActiveFalse(Pageable pageable);

    @EntityGraph(attributePaths = {"orderProducts", "orderProducts.product"})
    Optional<Order> findByIdAndActiveTrue(Long id);

    @EntityGraph(attributePaths = {"orderProducts", "orderProducts.product"})
    Optional<Order> findByIdAndActiveFalse(Long id);
}