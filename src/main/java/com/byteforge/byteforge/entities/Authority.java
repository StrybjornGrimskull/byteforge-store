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
@Table(name = "authorities")
@FieldDefaults(level = PRIVATE)
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authorities_seq")
    @SequenceGenerator(name = "authorities_seq", sequenceName = "authorities_id_seq", allocationSize = 1)
    Integer id;

    String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    Customer customer;

    public Authority(String name, Customer customer) {
        this.name = name;
        this.customer = customer;
    }
}