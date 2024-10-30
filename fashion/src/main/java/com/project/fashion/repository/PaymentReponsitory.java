package com.project.fashion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.fashion.model.Payment;

public interface PaymentReponsitory extends JpaRepository<Payment, Long> {

    List<Payment> findAllByCustomer_CustomerId(Long customerId);

    Payment findByPaymentMethodAndCustomer_CustomerId(String method, Long customerId);

    List<Payment> findAllByPaymentMethod(String method);

}
