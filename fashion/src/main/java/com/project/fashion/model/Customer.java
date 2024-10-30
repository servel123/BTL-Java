package com.project.fashion.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;

import java.util.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class Customer extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(name = "firstName", length = 100)
    private String firstName;

    @Column(name = "lastName", length = 100)
    private String lastName;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "username", nullable = false, length = 100, unique = true)
    private String username;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "phoneNumber", length = 10)
    private String phoneNumber;

    @Column(name = "role", length = 8)
    private String role;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Payment> payments;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Cart> carts;

    @PrePersist
    public void prePersist() {
        if (role == null) {
            role = "USER";
        }
    }
}
