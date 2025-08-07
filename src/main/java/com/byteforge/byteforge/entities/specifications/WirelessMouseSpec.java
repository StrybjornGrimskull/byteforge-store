package com.byteforge.byteforge.entities.specifications;

import com.byteforge.byteforge.entities.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "wireless_mice_specs")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WirelessMouseSpec {
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

    @Column(nullable = false)
    private Integer buttons;

    @Column(nullable = false, length = 30)
    private String wirelessTech;

    @Column(nullable = false)
    private Integer pollingRate;

    @Column(nullable = false)
    private Integer weight;

    private Boolean rgbLighting = false;

    @Column(nullable = false, length = 30)
    private String batteryType;

    @Column(nullable = false)
    private Integer batteryLife;

    @Column(nullable = false)
    private Integer standbyBatteryLife;

    @Column(nullable = false)
    private Integer chargingTime;

    private Boolean onboardMemory = false;

    @Column(nullable = false)
    private Integer warrantyMonths;
}