package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.StockQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockQuantityRepository extends JpaRepository<StockQuantity, Integer> {
}