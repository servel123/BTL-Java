package com.project.fashion.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.*;

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

    @Column(name = "paymentDate", nullable = false)
    // @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime paymentDate;

    @Column(name = "paymentMethod", nullable = false, length = 100)
    private String paymentMethod;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private Long amount;

    @Override
    public String toString() {
        return "Payment: " + paymentId + " " + customer + " " + paymentDate + " " + paymentMethod + " " + amount;
    }
}
