package com.byteforge.byteforge.entities.specifications;

import com.byteforge.byteforge.entities.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "motherboard_specs")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MotherboardSpec {

    @Id
    private Integer productId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false, length = 50)
    private String socket;

    @Column(nullable = false, length = 50)
    private String chipset;

    @Column(nullable = false, length = 20)
    private String formFactor;

    @Column(nullable = false)
    private Integer memorySlots;

    @Column(nullable = false)
    private Integer maxMemory;

    @Column(nullable = false, length = 20)
    private String memoryType;

    @Column( name = "m2_slots", nullable = false)
    private Integer m2Slots;

    @Column(nullable = false)
    private Integer sataPorts;
}