package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.specifications.CpuSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CpuSpecRepository extends JpaRepository<CpuSpec, Integer> {
}