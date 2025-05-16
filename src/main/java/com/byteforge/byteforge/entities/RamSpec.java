package com.byteforge.byteforge.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ram_specs")
public class RamSpec {
    @Id
    private Integer productId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private Integer memorySize; // GB

    @Column(nullable = false)
    private Integer modulesCount;

    @Column(nullable = false)
    private Integer speed; // MHz

    @Column(nullable = false, length = 20)
    private String type;

    @Column(nullable = false, length = 20)
    private String timings;

    @Column(nullable = false, precision = 3, scale = 2)
    private BigDecimal voltage; // V
}