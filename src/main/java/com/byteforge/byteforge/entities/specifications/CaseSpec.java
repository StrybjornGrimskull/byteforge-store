package com.byteforge.byteforge.entities.specifications;

import com.byteforge.byteforge.entities.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "case_specs")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CaseSpec {

    @Id
    private Integer productId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false, length = 50)
    private String formFactor;

    @Column(nullable = false)
    private String motherboardSupport;

    @Column(nullable = false)
    private Integer maxGpuLength;

    @Column(nullable = false)
    private Integer maxCpuCoolerHeight;

    @Column(nullable = false)
    private Integer fansIncluded;

    @Column(nullable = false)
    private String radiatorSupport;
}