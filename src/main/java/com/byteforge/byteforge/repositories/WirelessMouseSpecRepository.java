package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.specifications.WirelessMouseSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WirelessMouseSpecRepository extends JpaRepository<WirelessMouseSpec, Integer> {
    Optional<WirelessMouseSpec> findByProductId(Integer productId);
}