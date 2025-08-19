package com.byteforge.byteforge.entities.specifications;

import com.byteforge.byteforge.entities.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cpu_specs")
@FieldDefaults(level = PRIVATE)
public class CpuSpec {

    @Id
    Integer productId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_id")
    Product product;

    @Column(nullable = false)
    Integer cores;

    @Column(nullable = false)
    Integer threads;

    @Column(nullable = false, precision = 4, scale = 2)
    BigDecimal baseClock;

    @Column(nullable = false, precision = 4, scale = 2)
    BigDecimal boostClock;

    @Column(nullable = false, length = 50)
    String socket;

    @Column(nullable = false)
    Integer cacheSize;

    @Column(nullable = false)
    Integer tdp;

    Boolean integratedGpu = false;
}