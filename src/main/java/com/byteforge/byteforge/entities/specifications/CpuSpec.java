package com.byteforge.byteforge.entities.specifications;

import com.byteforge.byteforge.entities.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cpu_specs")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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