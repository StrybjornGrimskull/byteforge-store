package com.byteforge.byteforge.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "profiles")
@FieldDefaults(level = PRIVATE)
public class Profile {

    @Id
    @Column(name = "customer_id")
    Integer customerId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "customer_id")
    Customer customer;

    @Column(name = "first_name", length = 100)
    String firstName;

    @Column(name = "last_name", length = 100)
    String lastName;

    @Column(name = "phone_number", length = 20)
    String phone;

    @Column(name = "city", columnDefinition = "TEXT")
    String city;

    @Column(name = "address", columnDefinition = "TEXT")
    String address;

    @Column(name = "post_index")
    Integer postIndex;

    @Column(name = "birth_date")
    LocalDate birthDate;
}