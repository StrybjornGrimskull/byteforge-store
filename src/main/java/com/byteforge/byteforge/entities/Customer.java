package com.byteforge.byteforge.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="customers")
@FieldDefaults(level = PRIVATE)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String password;

    @Column(name="email_verified", nullable = false)
    boolean emailVerified = false;

    @Column (name ="email_verification_token")
    String emailVerificationToken;

    @Column(name = "password_reset_token")
    String passwordResetToken;

    @Column(name = "password_reset_token_expiry")
    LocalDateTime passwordResetTokenExpiry;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    Profile profile;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    @JsonIgnore
    Set<Authority> authorities;
}
