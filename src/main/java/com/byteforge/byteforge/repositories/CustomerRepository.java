package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.dto.response.CustomerNameFromProfileDto;
import com.byteforge.byteforge.entities.Customer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,Integer> {

    @EntityGraph(attributePaths = {"authorities","profile"})
    Optional<Customer> findByEmail(String email);

    @Query("SELECT new com.byteforge.byteforge.dto.response.CustomerNameFromProfileDto(p.firstName) " +
            "FROM Customer c " +
            "JOIN c.profile p " +
            "WHERE c.email = :email")
    CustomerNameFromProfileDto findCustomerNameByEmail(@Param("email") String email);

    Optional<Customer> findByEmailVerificationToken(String token);

    Optional<Customer> findByPasswordResetToken(String token);
}
