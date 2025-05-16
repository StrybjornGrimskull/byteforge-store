package com.byteforge.byteforge.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "case_specs")
public class CaseSpec {
    @Id
    private Integer productId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false, length = 50)
    private String formFactor;

    @Column(nullable = false, length = 255)
    private String motherboardSupport;

    @Column(nullable = false)
    private Integer maxGpuLength;

    @Column(nullable = false)
    private Integer maxCpuCoolerHeight;

    @Column(nullable = false)
    private Integer fansIncluded;

    @Column(nullable = false, length = 255)
    private String radiatorSupport;
}