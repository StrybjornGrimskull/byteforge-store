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
@Table(name = "case_specs")
@FieldDefaults(level = PRIVATE)
public class CaseSpec {

    @Id
    Integer productId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_id")
    Product product;

    @Column(nullable = false, length = 50)
    String formFactor;

    @Column(nullable = false)
    String motherboardSupport;

    @Column(nullable = false)
    Integer maxGpuLength;

    @Column(nullable = false)
    Integer maxCpuCoolerHeight;

    @Column(nullable = false)
    Integer fansIncluded;

    @Column(nullable = false)
    String radiatorSupport;
}