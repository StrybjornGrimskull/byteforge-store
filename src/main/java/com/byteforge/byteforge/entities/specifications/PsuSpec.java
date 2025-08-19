package com.byteforge.byteforge.entities.specifications;

import com.byteforge.byteforge.entities.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "psu_specs")
@FieldDefaults(level = PRIVATE)
public class PsuSpec {

    @Id
    Integer productId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_id")
    Product product;

    @Column(nullable = false)
    Integer wattage; // W

    @Column(nullable = false, length = 20)
    String formFactor;

    @Column(nullable = false, length = 20)
    String efficiencyCert;

    @Column(nullable = false, length = 20)
    String modularity;

    @Column(name = "pcie_8pin_connectors", nullable = false)
    Integer pcie8pinConnectors;

    @Column(nullable = false)
    Integer sataConnectors;
}
