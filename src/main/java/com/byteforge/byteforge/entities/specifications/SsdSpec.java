package com.byteforge.byteforge.entities.specifications;

import com.byteforge.byteforge.entities.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ssd_specs")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SsdSpec {

    @Id
    private Integer productId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false, length = 20)
    private String formFactor;

    @Column(name="interface", nullable = false, length = 20)
    private String interfaceType;

    @Column(nullable = false)
    private Integer readSpeed;

    @Column(nullable = false)
    private Integer writeSpeed;

    @Column(length = 30)
    private String memoryType;

    @Column(nullable = false)
    private Integer enduranceTbw;

    private Boolean dramCache=false;

    @Column(length = 30)
    private String encryption;

    @Column(precision = 3, scale = 1)
    private BigDecimal thickness;
}
