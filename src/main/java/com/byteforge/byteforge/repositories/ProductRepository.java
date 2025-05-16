package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    String FULL_GRAPH = "Product.full";

    @Override
    @EntityGraph(value = FULL_GRAPH, type = EntityGraph.EntityGraphType.LOAD)
    Page<Product> findAll(Specification<Product> spec, Pageable pageable);

    @Override
    @EntityGraph(value = FULL_GRAPH, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Product> findById(Integer id);
}
