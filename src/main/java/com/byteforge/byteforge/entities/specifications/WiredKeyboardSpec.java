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
@Table(name = "wired_keyboards_spec")
@FieldDefaults(level = PRIVATE)
public class WiredKeyboardSpec {

    @Id
    Integer productId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_id")
    Product product;

    @Column(nullable = false, length = 20)
    String layout;

    @Column(nullable = false, length = 30)
    String switchType;

    @Column(nullable = false, length = 30)
    String switchBrand;

    @Column(nullable = false, length = 50)
    String switchModel;

    Boolean rgbLighting = false;

    Boolean hotSwappable = false;

    @Column(nullable = false, precision = 3, scale = 1)
    BigDecimal actuationForce;

    @Column(nullable = false, precision = 3, scale = 1)
    BigDecimal travelDistance;

    @Column(nullable = false)
    Integer weight;

    @Column(nullable = false, precision = 4, scale = 1)
    BigDecimal cableLength;

    Boolean usbPassthrough = false;

    Boolean detachableCable = false;
}