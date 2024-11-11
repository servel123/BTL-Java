package com.project.fashion.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.fashion.model.OrderLine;
import com.project.fashion.service.implement.OrderLineServiceImplement;

import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/admin/bills")
public class AdminBillsController {
    @Autowired
    private OrderLineServiceImplement orderLineServiceImplement;

    @GetMapping
    public String adminBills(Model model) {
        try {
            List<OrderLine> bills = orderLineServiceImplement.getOrderLinesOfEveryOne();
            model.addAttribute("bills", bills);
            return "adminLayout/adminBills";
        } catch (Exception e) {
            model.addAttribute("message", "get bills failed");
            int year = LocalDate.now().getYear();
            return "redirect:/admin/dashboard?year=" + year;
        }
    }
}
