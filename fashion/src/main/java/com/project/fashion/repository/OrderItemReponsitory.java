package com.project.fashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.fashion.model.OrderItem;

public interface OrderItemReponsitory extends JpaRepository<OrderItem, Long> {

}