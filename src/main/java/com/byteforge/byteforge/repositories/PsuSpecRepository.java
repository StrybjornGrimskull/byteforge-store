package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.specifications.PsuSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PsuSpecRepository extends JpaRepository<PsuSpec, Integer> {
    Optional<PsuSpec> findByProductId(Integer productId);
}