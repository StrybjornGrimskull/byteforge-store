package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.specifications.WiredMouseSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WiredMouseSpecRepository extends JpaRepository<WiredMouseSpec, Integer> {
}