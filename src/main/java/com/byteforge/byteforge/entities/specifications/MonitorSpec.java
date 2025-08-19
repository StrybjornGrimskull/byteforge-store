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
@Table(name = "monitor_specs")
@FieldDefaults(level = PRIVATE)
public class MonitorSpec {

    @Id
    Integer productId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_id")
    Product product;

    @Column(nullable = false, precision = 4, scale = 1)
    BigDecimal screenSize;

    @Column(nullable = false, length = 20)
    String resolution;

    @Column(nullable = false, length = 30)
    String panelType;

    @Column(nullable = false)
    Integer refreshRate;

    @Column(nullable = false)
    Integer responseTime;
}


