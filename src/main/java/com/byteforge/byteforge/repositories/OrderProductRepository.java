package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}