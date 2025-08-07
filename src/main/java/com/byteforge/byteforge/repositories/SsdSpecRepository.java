package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.specifications.SsdSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SsdSpecRepository extends JpaRepository<SsdSpec, Integer> {
    Optional<SsdSpec> findByProductId(Integer productId);
}