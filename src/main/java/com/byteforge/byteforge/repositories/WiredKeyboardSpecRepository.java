package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.specifications.WiredKeyboardSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WiredKeyboardSpecRepository extends JpaRepository<WiredKeyboardSpec, Integer> {
    Optional<WiredKeyboardSpec> findByProductId(Integer productId);
}