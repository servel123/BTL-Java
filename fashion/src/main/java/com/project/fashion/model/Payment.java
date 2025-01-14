package com.project.fashion.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Payment extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;

    @Column(name = "paymentMethod", nullable = false, length = 100)
    private String paymentMethod;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
    private List<OrderLine> orderLines;

    @Override
    public String toString() {
        return "Payment: " + paymentId + " " + customer + " " + " " + paymentMethod + " ";
    }
}
