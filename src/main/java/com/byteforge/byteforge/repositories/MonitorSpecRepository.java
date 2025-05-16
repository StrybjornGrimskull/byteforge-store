package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.MonitorSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitorSpecRepository extends JpaRepository<MonitorSpec, Integer> {
}