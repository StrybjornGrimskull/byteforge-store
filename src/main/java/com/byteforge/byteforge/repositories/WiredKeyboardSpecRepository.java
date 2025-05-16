package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.WiredKeyboardSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WiredKeyboardSpecRepository extends JpaRepository<WiredKeyboardSpec, Integer> {
}