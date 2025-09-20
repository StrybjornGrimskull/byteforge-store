package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.dto.BrandDto;
import com.byteforge.byteforge.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {

    @Query("SELECT new com.byteforge.byteforge.dto.BrandDto(b.id, b.name, b.logoUrl) " +
            "FROM Brand b ORDER BY b.name ASC")
    List<BrandDto> findAllBrandDtosOrderedByName();
    
    @Query("SELECT DISTINCT new com.byteforge.byteforge.dto.BrandDto(b.id, b.name, b.logoUrl) " +
            "FROM Brand b JOIN b.products p WHERE (:categoryId IS NULL OR p.category.id = :categoryId) ORDER BY b.name ASC")
    List<BrandDto> findBrandDtosByProductsCategoryId(@Param("categoryId") Integer categoryId);
    
    boolean existsByNameIgnoreCase(String name);
}
