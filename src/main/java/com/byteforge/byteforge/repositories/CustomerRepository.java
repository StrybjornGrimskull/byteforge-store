package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.Customer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,Integer> {
    @EntityGraph(attributePaths = {"authorities"})
    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByEmailVerificationToken(String token);

}
