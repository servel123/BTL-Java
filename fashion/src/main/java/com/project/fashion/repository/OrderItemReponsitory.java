package com.project.fashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.fashion.model.OrderItem;
import java.util.List;

public interface OrderItemReponsitory extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findAllByOrderLine_OrderLineId(Long orderLineId);
}