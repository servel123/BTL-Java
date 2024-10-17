package com.project.fashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.fashion.model.OrderLine;

public interface OrderLineReponsitory extends JpaRepository<OrderLine, Long> {

}
