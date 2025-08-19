package com.byteforge.byteforge.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@FieldDefaults(level = PRIVATE)
public class Order {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "total_price", nullable = false, precision = 12, scale = 2)
    BigDecimal totalPrice;

    @Column(name = "date", columnDefinition = "timestamp default current_timestamp")
    LocalDateTime date = LocalDateTime.now();

    @Column(name = "first_name", nullable = false)
    String firstName;

    @Column(name = "last_name", nullable = false)
    String lastName;

    @Column(name = "city", nullable = false)
    String city;

    @Column(name = "address", nullable = false)
    String address;

    @Column(name = "email", nullable = false)
    String email;

    @Column(name = "phone_number", nullable = false)
    String phoneNumber;

    @Column(name = "post_index", nullable = false)
    Integer postIndex;

    @Column(name = "active", nullable = false)
    boolean active = true;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<OrderProduct> orderProducts;

    @ManyToOne
    Customer customer;
}
