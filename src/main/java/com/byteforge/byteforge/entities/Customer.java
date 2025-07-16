package com.byteforge.byteforge.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "customers")
@Getter @Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name="email_verified", nullable = false)
    private boolean emailVerified = false;

    @Column (name ="email_verification_token")
    private String emailVerificationToken;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL)
    private Profile profile;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    @JsonIgnore
    private Set<Authority> authorities;
}
