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
@Table(name = "order_products")
@FieldDefaults(level = PRIVATE)
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_products_seq")
    @SequenceGenerator(name = "order_products_seq", sequenceName = "order_products_id_seq", allocationSize = 1)
    Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    Integer quantity;
}