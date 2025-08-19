package com.byteforge.byteforge.entities.specifications;

import com.byteforge.byteforge.entities.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wireless_mice_specs")
@FieldDefaults(level = PRIVATE)
public class WirelessMouseSpec {
    @Id
    Integer productId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_id")
    Product product;

    @Column(nullable = false, length = 50)
    String sensorType;

    @Column(nullable = false, length = 50)
    String sensorModel;

    @Column(nullable = false)
    Integer maxDpi;

    @Column(nullable = false)
    Integer buttons;

    @Column(nullable = false, length = 30)
    String wirelessTech;

    @Column(nullable = false)
    Integer pollingRate;

    @Column(nullable = false)
    Integer weight;

    Boolean rgbLighting = false;

    @Column(nullable = false, length = 30)
    String batteryType;

    @Column(nullable = false)
    Integer batteryLife;

    @Column(nullable = false)
    Integer standbyBatteryLife;

    @Column(nullable = false)
    Integer chargingTime;

    Boolean onboardMemory = false;

    @Column(nullable = false)
    Integer warrantyMonths;
}