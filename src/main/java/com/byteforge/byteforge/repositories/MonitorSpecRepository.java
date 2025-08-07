package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.specifications.MonitorSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MonitorSpecRepository extends JpaRepository<MonitorSpec, Integer> {
    Optional<MonitorSpec> findByProductId(Integer productId);
}