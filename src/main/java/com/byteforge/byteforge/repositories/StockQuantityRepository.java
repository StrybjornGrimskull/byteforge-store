package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.StockQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockQuantityRepository extends JpaRepository<StockQuantity, Integer> {
    
    Optional<StockQuantity> findByProductId(Integer productId);
    
    boolean existsByProductId(Integer productId);
}