/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.fashion.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.fashion.dto.request.AddPaymentDTO;
import com.project.fashion.exception.ResourceNotFoundException;
import com.project.fashion.model.Customer;
import com.project.fashion.model.Payment;
import com.project.fashion.repository.PaymentReponsitory;
import com.project.fashion.service.PaymentService;

/**
 *
 * @author Vu
 */

@Service
public class PaymentServiceImplement implements PaymentService {

    @Autowired
    private PaymentReponsitory paymentReponsitory;
    @Autowired
    private CustomerServiceImplement customerServiceImplement;

    protected Payment getPaymentById(Long paymentId) {
        return paymentReponsitory.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Not Payment"));
    }

    @Override
    public Payment addPayment(AddPaymentDTO payment) {

        Customer cus = customerServiceImplement.getCustomerById(payment.getCustomerId());

        Payment pay = Payment.builder()
                .customer(cus)
                .paymentMethod(payment.getMethod())
                .build();
        paymentReponsitory.save(pay);
        return pay;
    }

    @Override
    public List<Payment> getAllPayment() {
        return paymentReponsitory.findAll();
    }

    @Override
    public List<Payment> getListPaymentOfCustomer(Long customerId) {
        List<Payment> pay = paymentReponsitory.findAllByCustomer_CustomerId(customerId);
        if (pay.size() == 0) {
            String message = "Customer " + customerId + "not payment";
            throw new ResourceNotFoundException(message);
        }
        return pay;
    }

    @Override
    public Payment getPaymentByMethodOfCustomer(Long customerId, String method) {
        Payment pay = paymentReponsitory.findByPaymentMethodAndCustomer_CustomerId(method, customerId);
        if (pay == null) {
            String message = "Customer " + customerId + "not payment have " + method;
            throw new ResourceNotFoundException(message);
        }
        return pay;
    }

    @Override
    public List<Payment> getPaymentByMethod(String method) {
        List<Payment> pay = paymentReponsitory.findAllByPaymentMethod(method);
        if (pay.size() == 0) {
            String message = "Not" + method;
            throw new ResourceNotFoundException(message);
        }
        return pay;
    }

    // update
    @Override
    public Payment updatePayment(AddPaymentDTO payment) {
        Payment pay = getPaymentById(payment.getPaymentId());
        pay.setPaymentMethod(payment.getMethod());
        paymentReponsitory.save(pay);
        return pay;
    }

    // delete
    @Override
    public void deletePayment(Long paymentId) {
        paymentReponsitory.deleteById(paymentId);
    }

}
