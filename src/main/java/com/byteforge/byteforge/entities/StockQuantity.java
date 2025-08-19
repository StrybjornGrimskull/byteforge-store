package com.byteforge.byteforge.entities;

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
@Table(name="stock_quantity")
@FieldDefaults(level = PRIVATE)
public class StockQuantity {

    @Id
    @Column(name = "product_id")
    Integer productId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    Product product;

    @Column(name = "quantity")
    Integer quantity = 0;
}