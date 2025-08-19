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
@Table(name = "ssd_specs")
@FieldDefaults(level = PRIVATE)
public class SsdSpec {

    @Id
    Integer productId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_id")
    Product product;

    @Column(nullable = false)
    Integer capacity;

    @Column(nullable = false, length = 20)
    String formFactor;

    @Column(name = "interface", nullable = false, length = 20)
    String interfaceType;

    @Column(nullable = false)
    Integer readSpeed;

    @Column(nullable = false)
    Integer writeSpeed;

    @Column(length = 30)
    String memoryType;

    @Column(nullable = false)
    Integer enduranceTbw;

    Boolean dramCache = false;

    @Column(length = 30)
    String encryption;

    @Column(precision = 3, scale = 1)
    BigDecimal thickness;
}
