package com.byteforge.byteforge.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@DynamicUpdate
public class Profile {

    @Id
    @Column(name = "customer_id")
    private Integer customerId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "first_name", length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(name = "phone_number", length = 20)
    private String phone;

    @Column(name = "city", columnDefinition = "TEXT")
    private String city;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "post_index")
    private Integer postIndex;

    @Column(name = "birth_date")
    private LocalDate birthDate;
}