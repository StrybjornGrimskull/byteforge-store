package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.RamSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RamSpecRepository extends JpaRepository<RamSpec, Integer> {
}