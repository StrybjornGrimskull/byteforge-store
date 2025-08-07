package com.byteforge.byteforge.entities.specifications;

import com.byteforge.byteforge.entities.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "monitor_specs")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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


