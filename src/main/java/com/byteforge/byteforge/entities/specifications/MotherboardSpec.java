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
@Table(name = "motherboard_specs")
@FieldDefaults(level = PRIVATE)
public class MotherboardSpec {

    @Id
    Integer productId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_id")
    Product product;

    @Column(nullable = false, length = 50)
    String socket;

    @Column(nullable = false, length = 50)
    String chipset;

    @Column(nullable = false, length = 20)
    String formFactor;

    @Column(nullable = false)
    Integer memorySlots;

    @Column(nullable = false)
    Integer maxMemory;

    @Column(nullable = false, length = 20)
    String memoryType;

    @Column(name = "m2_slots", nullable = false)
    Integer m2Slots;

    @Column(nullable = false)
    Integer sataPorts;
}