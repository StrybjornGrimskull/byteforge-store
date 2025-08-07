package com.byteforge.byteforge.entities.specifications;

import com.byteforge.byteforge.entities.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "wired_mice_specs")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WiredMouseSpec {
    @Id
    private Integer productId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false, length = 50)
    private String sensorType;

    @Column(nullable = false, length = 50)
    private String sensorModel;

    @Column(nullable = false)
    private Integer maxDpi;

    private Boolean adjustableDpi = false;

    @Column(nullable = false)
    private Integer buttons;

    @Column(nullable = false)
    private Integer cableLength; // cm

    @Column(nullable = false, length = 30)
    private String cableType;

    @Column(nullable = false, length = 20)
    private String usbConnector;

    @Column(nullable = false)
    private Integer weight;

    private Boolean rgbLighting = false;

    private Boolean onboardMemory = false;

    @Column(nullable = false)
    private Integer warrantyMonths;
}