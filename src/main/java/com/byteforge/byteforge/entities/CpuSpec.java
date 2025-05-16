package com.byteforge.byteforge.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cpu_specs")
public class CpuSpec {
    @Id
    private Integer productId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private Integer cores;

    @Column(nullable = false)
    private Integer threads;

    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal baseClock;

    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal boostClock;

    @Column(nullable = false, length = 50)
    private String socket;

    @Column(nullable = false)
    private Integer cacheSize;

    @Column(nullable = false)
    private Integer tdp;

    private Boolean integratedGpu = false;
}