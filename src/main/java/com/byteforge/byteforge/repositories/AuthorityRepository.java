package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
}