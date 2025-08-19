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
@Table(name = "gpu_specs")
@FieldDefaults(level = PRIVATE)
public class GpuSpec {

    @Id
    Integer productId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_id")
    Product product;

    @Column(nullable = false)
    Integer memorySize;

    @Column(nullable = false, length = 20)
    String memoryType;

    @Column(nullable = false)
    Integer busWidth;

    @Column(nullable = false)
    Integer baseClock;

    @Column(nullable = false)
    Integer boostClock;

    @Column(nullable = false)
    Integer tdp;

    @Column(nullable = false)
    Integer length;

    @Column(nullable = false)
    String displayOutputs;
}