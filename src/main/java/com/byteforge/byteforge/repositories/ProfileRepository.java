package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.dto.response.CustomerNameFromProfileDto;
import com.byteforge.byteforge.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {

     @Query("SELECT new com.byteforge.byteforge.dto.response.CustomerNameFromProfileDto(p.firstName) " +
           "FROM Profile p " +
           "JOIN p.customer c " +
           "WHERE c.email = :email")
    CustomerNameFromProfileDto findCustomerNameByEmail(@Param("email") String email);
}