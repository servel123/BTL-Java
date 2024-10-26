/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.project.fashion.service;

/**
 *
 * @author Vu
 */
import java.util.*;

import com.project.fashion.dto.request.AddPaymentDTO;
import com.project.fashion.model.Payment;

public interface PaymentService {
    // create
    Payment addPayment(AddPaymentDTO payment);

    // read
    List<Payment> getAllPayment();

    List<Payment> getListPaymentOfCustomer(Long customerId);

    Payment getPaymentByMethodOfCustomer(Long customerId, String method);

    List<Payment> getPaymentByMethod(String method);

    // update
    Payment updatePayment(AddPaymentDTO payment);

    // delete
    void deletePayment(Long paymentId);

}
