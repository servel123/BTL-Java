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
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class OrderLine extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderLineId;

    @ManyToOne
    @JoinColumn(name = "paymentId", nullable = false)
    private Payment payment;

    @Column(name = "tranCode")
    private String tranCode;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "orderLine", cascade = CascadeType.ALL)
    List<OrderItem> orderItems;

    @Override
    public String toString() {
        return "OrderLine: " + orderLineId + " " + payment;
    }
}
