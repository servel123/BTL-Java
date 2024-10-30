package com.project.fashion.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.fashion.admin.service.BillingService;
import com.project.fashion.model.OrderLine;

import java.util.*;

@Controller
@RequestMapping("/admin/bills")
public class AdminBillsController {
    private BillingService billingService;

    @GetMapping
    public String adminBills(Model model) {
        try {
            List<OrderLine> bills = billingService.getAllOrderLine();
            model.addAttribute("bills", bills);
            return "bills";
        } catch (Exception e) {
            model.addAttribute("message", "get bills failed");
            return "redirect:/admin/dashboard";
        }
    }
}
