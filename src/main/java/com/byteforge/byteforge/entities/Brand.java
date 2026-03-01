package com.byteforge.byteforge.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "brands")
@FieldDefaults(level = PRIVATE)
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brands_seq")
    @SequenceGenerator(name = "brands_seq", sequenceName = "brands_id_seq", allocationSize = 1)
    Integer id;

    @Column(nullable = false, length = 100, unique = true)
    String name;

    @Column(nullable = false)
    String logoUrl;

    @OneToMany(mappedBy = "brand")
    Set<Product> products;
}