package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.PsuSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PsuSpecRepository extends JpaRepository<PsuSpec, Integer> {
}