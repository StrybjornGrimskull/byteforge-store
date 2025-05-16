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
@Table(name = "psu_specs")
public class PsuSpec {
    @Id
    private Integer productId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private Integer wattage; // W

    @Column(nullable = false, length = 20)
    private String formFactor;

    @Column(nullable = false, length = 20)
    private String efficiencyCert;

    @Column(nullable = false, length = 20)
    private String modularity;

    @Column(name ="pcie_8pin_connectors", nullable = false)
    private Integer pcie8pinConnectors;

    @Column(nullable = false)
    private Integer sataConnectors;
}
