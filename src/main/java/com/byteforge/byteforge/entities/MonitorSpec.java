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
@Table(name = "monitor_specs")
public class MonitorSpec {
    @Id
    private Integer productId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false, precision = 4, scale = 1)
    private BigDecimal screenSize;

    @Column(nullable = false, length = 20)
    private String resolution;

    @Column(nullable = false, length = 30)
    private String panelType;

    @Column(nullable = false)
    private Integer refreshRate;

    @Column(nullable = false)
    private Integer responseTime;
}
