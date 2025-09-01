package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.dto.response.CustomerNameFromProfileDto;
import com.byteforge.byteforge.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    @EntityGraph(attributePaths = {"authorities", "profile"})
    Optional<Customer> findByEmail(String email);

    @Query("SELECT new com.byteforge.byteforge.dto.response.CustomerNameFromProfileDto(p.firstName) " +
            "FROM Customer c " +
            "JOIN c.profile p " +
            "WHERE c.email = :email")
    CustomerNameFromProfileDto findCustomerNameByEmail(@Param("email") String email);


    @Query("SELECT c FROM Customer c " +
            "LEFT JOIN FETCH c.authorities LEFT JOIN FETCH c.profile " +
            "WHERE (:search IS NULL OR :search = '' OR LOWER(c.email) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "AND (:role IS NULL OR :role = '' OR EXISTS (SELECT 1 FROM c.authorities a WHERE a.name = :role))")
    Page<Customer>  findAllCustomersWithAuthoritiesAndFilters(
            @Param("search") String search,
            @Param("role") String role,
            Pageable pageable);

    Optional<Customer> findByEmailVerificationToken(String token);

    Optional<Customer> findByPasswordResetToken(String token);
}
