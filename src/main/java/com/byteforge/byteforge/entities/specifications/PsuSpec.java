package com.byteforge.byteforge.entities.specifications;

import com.byteforge.byteforge.entities.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "psu_specs")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
