package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.WirelessMouseSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WirelessMouseSpecRepository extends JpaRepository<WirelessMouseSpec, Integer> {
}