package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    Profile findByCustomerId(Integer customerId);
}