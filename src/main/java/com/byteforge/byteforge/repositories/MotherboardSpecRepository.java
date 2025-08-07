package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.specifications.MotherboardSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MotherboardSpecRepository extends JpaRepository<MotherboardSpec, Integer> {
    Optional<MotherboardSpec> findByProductId(Integer productId);
}