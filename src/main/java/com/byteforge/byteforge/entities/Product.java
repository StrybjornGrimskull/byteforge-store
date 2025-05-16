package com.byteforge.byteforge.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "Product.full",
                attributeNodes = {
                        @NamedAttributeNode("brand"),
                        @NamedAttributeNode("category"),
                        @NamedAttributeNode("stockQuantity"),
                        @NamedAttributeNode("caseSpec"),
                        @NamedAttributeNode("cpuSpec"),
                        @NamedAttributeNode("gpuSpec"),
                        @NamedAttributeNode("monitorSpec"),
                        @NamedAttributeNode("motherboardSpec"),
                        @NamedAttributeNode("psuSpec"),
                        @NamedAttributeNode("ramSpec"),
                        @NamedAttributeNode("ssdSpec"),
                        @NamedAttributeNode("wiredKeyboardSpec"),
                        @NamedAttributeNode("wiredMouseSpec"),
                        @NamedAttributeNode("wirelessKeyboardSpec"),
                        @NamedAttributeNode("wirelessMouseSpec")
                }
        )
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer warrantyMonths = 24;

    @Column(nullable = false)
    private Integer releaseYear;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String shortDescription;

    @Column(nullable = false, length = 255)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    private StockQuantity stockQuantity;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    private CaseSpec caseSpec;
    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    private CpuSpec cpuSpec;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    private GpuSpec gpuSpec;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    private MonitorSpec monitorSpec;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    private MotherboardSpec motherboardSpec;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    private PsuSpec psuSpec;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    private RamSpec ramSpec;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    private SsdSpec ssdSpec;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    private WiredKeyboardSpec wiredKeyboardSpec;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    private WiredMouseSpec wiredMouseSpec;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    private WirelessKeyboardSpec wirelessKeyboardSpec;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    private WirelessMouseSpec wirelessMouseSpec;
}