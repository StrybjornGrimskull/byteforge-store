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
@Table(name = "wired_keyboards_spec")
public class WiredKeyboardSpec {

    @Id
    private Integer productId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false, length = 20)
    private String layout;

    @Column(nullable = false, length = 30)
    private String switchType;

    @Column(nullable = false, length = 30)
    private String switchBrand;

    @Column(nullable = false, length = 50)
    private String switchModel;

    private Boolean rgbLighting = false;

    private Boolean hotSwappable = false;

    @Column(nullable = false, precision = 3, scale = 1)
    private BigDecimal actuationForce;

    @Column(nullable = false, precision = 3, scale = 1)
    private BigDecimal travelDistance;

    @Column(nullable = false)
    private Integer weight;

    @Column(nullable = false, precision = 4, scale = 1)
    private BigDecimal cableLength;

    private Boolean usbPassthrough = false;

    private Boolean detachableCable = false;
}