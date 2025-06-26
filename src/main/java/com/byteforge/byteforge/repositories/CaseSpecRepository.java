package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.specifications.CaseSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseSpecRepository extends JpaRepository<CaseSpec, Integer> {
}