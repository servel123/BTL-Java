package com.project.fashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.fashion.model.Payment;

public interface PaymentReponsitory extends JpaRepository<Payment, Long> {

}
