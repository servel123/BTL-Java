/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.project.fashion.service;

import com.project.fashion.model.OrderItem;
import com.project.fashion.model.OrderLine;
import com.project.fashion.model.Payment;
import java.util.List;

/**
 *
 * @author Vu
 */
public interface OrderLineService {
    List<OrderItem> getOrderItemByOrderLine(Long orderLineId);
    OrderLine addOrderLine(Payment paymentId);
}
