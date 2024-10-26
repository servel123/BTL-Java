/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.project.fashion.service;

import java.util.*;

import com.project.fashion.dto.request.AddOrderLineDTO;
import com.project.fashion.model.OrderLine;

/**
 *
 * @author Vu
 */
public interface OrderLineService {
    // create
    OrderLine addOrderLine(AddOrderLineDTO payment);

    // read

    OrderLine getOrderLineOfCustomer(Long orderLineId);

    List<OrderLine> getOrderLinesOfCustomer(Long customerId);

    List<OrderLine> getOrderLinesOfEveryOne();

    // update
    OrderLine updateOrderLineOfCustomer(AddOrderLineDTO payment);

    // delete
    void deleteOrderLineOfCustomer(Long orderLineId);
}
