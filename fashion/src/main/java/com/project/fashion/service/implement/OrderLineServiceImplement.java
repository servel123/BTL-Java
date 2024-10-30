/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.fashion.service.implement;

import com.project.fashion.dto.request.AddOrderLineDTO;
import com.project.fashion.exception.ResourceNotFoundException;
import com.project.fashion.model.Customer;
import com.project.fashion.model.OrderLine;
import com.project.fashion.model.Payment;
import com.project.fashion.repository.OrderLineReponsitory;
import com.project.fashion.service.OrderLineService;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Vu
 */
@Service
public class OrderLineServiceImplement implements OrderLineService {

    @Autowired
    private OrderLineReponsitory orderLineReponsitory;
    private PaymentServiceImplement paymentServiceImplement;
    private CustomerServiceImplement customerServiceImplement;

    protected OrderLine getOrderLineById(Long orderLineId) {
        return orderLineReponsitory.findById(orderLineId)
                .orElseThrow(() -> new ResourceNotFoundException("Not OrderLine"));
    }

    // create

    @Override
    public OrderLine addOrderLine(AddOrderLineDTO payment) {
        Payment pay = paymentServiceImplement.getPaymentById(payment.getPaymentId());
        OrderLine orderLine = OrderLine.builder()
                .payment(pay)
                .status(payment.getStatus())
                .build();
        orderLineReponsitory.save(orderLine);
        return orderLine;
    }

    // read
    @Override
    public OrderLine getOrderLineOfCustomer(Long orderLineId) {
        return orderLineReponsitory.findById(orderLineId)
                .orElseThrow(() -> new ResourceNotFoundException("Not OrderLine"));
    }

    @Override
    public List<OrderLine> getOrderLinesOfCustomer(Long customerId) {
        Customer cus = customerServiceImplement.getCustomerById(customerId);
        List<Payment> payments = cus.getPayments();
        List<OrderLine> orderLines = payments.stream()
                .flatMap(payment -> payment.getOrderLines().stream())
                .collect(Collectors.toList());
        return orderLines;
    }

    @Override
    public List<OrderLine> getOrderLinesOfEveryOne() {
        return orderLineReponsitory.findAll();
    }

    // update only method
    @Override
    public OrderLine updateOrderLineOfCustomer(AddOrderLineDTO orderLineRequest) {
        OrderLine orderLine = orderLineReponsitory.findById(orderLineRequest.getOrderLineId())
                .orElseThrow(() -> new ResourceNotFoundException("Not bill"));
        orderLine.setPayment(paymentServiceImplement.getPaymentById(orderLineRequest.getPaymentId()));
        orderLineReponsitory.save(orderLine);
        return orderLine;

    }

    // delete
    @Override
    public void deleteOrderLineOfCustomer(Long orderLineId) {
        orderLineReponsitory.deleteById(orderLineId);
    }

}
