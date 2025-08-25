package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    @Query("SELECT DISTINCT op.product.id FROM OrderProduct op WHERE op.order.customer.id = :customerId")
    List<Integer> findUniqueProductIdsByCustomerId(@Param("customerId") Integer customerId);
}