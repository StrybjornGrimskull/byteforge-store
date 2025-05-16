package com.byteforge.byteforge.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gpu_specs")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GpuSpec {
    @Id
    private Integer productId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JsonIgnore
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private Integer memorySize;

    @Column(nullable = false, length = 20)
    private String memoryType;

    @Column(nullable = false)
    private Integer busWidth;

    @Column(nullable = false)
    private Integer baseClock;

    @Column(nullable = false)
    private Integer boostClock;

    @Column(nullable = false)
    private Integer tdp;

    @Column(nullable = false)
    private Integer length;

    @Column(nullable = false, length = 255)
    private String displayOutputs;
}