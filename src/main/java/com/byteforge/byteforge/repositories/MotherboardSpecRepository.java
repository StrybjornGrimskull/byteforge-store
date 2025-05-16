package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.MotherboardSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotherboardSpecRepository extends JpaRepository<MotherboardSpec, Integer> {
}