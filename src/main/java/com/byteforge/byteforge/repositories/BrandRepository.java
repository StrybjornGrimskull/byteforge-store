package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {

    @Query("SELECT DISTINCT b FROM Brand b JOIN b.products p WHERE p.category.id = :categoryId")
    List<Brand> findByProductsCategoryId(@Param("categoryId") Integer categoryId);
}
