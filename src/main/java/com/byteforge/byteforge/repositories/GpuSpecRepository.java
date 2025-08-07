package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.specifications.GpuSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GpuSpecRepository extends JpaRepository<GpuSpec, Integer> {
    Optional<GpuSpec> findByProductId(Integer productId);
}