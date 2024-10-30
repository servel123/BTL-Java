package com.project.fashion.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.fashion.model.OrderLine;
import com.project.fashion.service.implement.OrderLineServiceImplement;

public class BillingService {
    @Autowired
    private OrderLineServiceImplement orderLineServiceImplement;

    // lấy tất cả hóa đơn hóa đơn
    public List<OrderLine> getAllOrderLine() {
        List<OrderLine> orderLines = orderLineServiceImplement.getOrderLinesOfEveryOne();
        return orderLines;
    }
}
