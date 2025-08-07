package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.RamSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RamSpecRepository extends JpaRepository<RamSpec, Integer> {
    Optional<RamSpec> findByProductId(Integer productId);
}