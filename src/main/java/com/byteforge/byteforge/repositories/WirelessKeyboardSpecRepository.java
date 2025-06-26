package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.specifications.WirelessKeyboardSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WirelessKeyboardSpecRepository extends JpaRepository<WirelessKeyboardSpec, Integer> {
}