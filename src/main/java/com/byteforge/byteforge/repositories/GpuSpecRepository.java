package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.GpuSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GpuSpecRepository extends JpaRepository<GpuSpec, Integer> {
}