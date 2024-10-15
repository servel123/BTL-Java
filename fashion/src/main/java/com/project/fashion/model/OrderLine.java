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
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class OrderLine extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderLineId;

    @Column(name = "orderDate", nullable = false)
    // @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime orderDate;

    @Column(name = "totalPrice", nullable = false, precision = 10, scale = 2)
    private Long totalPrice;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "paymentId", nullable = false)
    private Payment payment;

    @Override
    public String toString() {
        return "OrderLine: " + orderLineId + " " + customer + " " + orderDate + " " + totalPrice + " " + payment;
    }
}
