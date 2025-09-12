package com.byteforge.byteforge.entities;

import com.byteforge.byteforge.entities.specifications.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
@FieldDefaults(level = PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false, length = 255)
    String name;

    @Column(nullable = false, name = "discount_percentage")
    Integer discountPercentage = 0;

    @Column(nullable = false, precision = 10, scale = 2, name = "original_price")
    BigDecimal originalPrice;

    @Column(nullable = false, precision = 10, scale = 2)
    BigDecimal price = originalPrice;

    @Column(nullable = false)
    Integer warrantyMonths = 24;

    @Column(nullable = false)
    Integer releaseYear;

    @Column(nullable = false, columnDefinition = "TEXT")
    String shortDescription;

    @Column(nullable = false, length = 255)
    String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    Category category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brand_id", nullable = false)
    Brand brand;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    StockQuantity stockQuantity;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    CaseSpec caseSpec;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    CpuSpec cpuSpec;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    GpuSpec gpuSpec;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    MonitorSpec monitorSpec;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    MotherboardSpec motherboardSpec;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    PsuSpec psuSpec;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    RamSpec ramSpec;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    SsdSpec ssdSpec;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    WiredKeyboardSpec wiredKeyboardSpec;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    WiredMouseSpec wiredMouseSpec;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    WirelessKeyboardSpec wirelessKeyboardSpec;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    WirelessMouseSpec wirelessMouseSpec;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Review> reviews;
}