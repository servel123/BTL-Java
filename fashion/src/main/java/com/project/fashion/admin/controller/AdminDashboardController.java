package com.project.fashion.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.fashion.admin.service.DashboardService;

import java.util.*;

@Controller
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {
    private DashboardService dashboardService;

    @GetMapping
    public String adminDashboard(Model model) {
        try {
            // tổng doanh thu
            Long revenue = dashboardService.totalRevenue();
            model.addAttribute("revenue", revenue);
        } catch (Exception e) {
            model.addAttribute("errRevenue", "Error revenue");
        }

        try {
            // số lượng đơn hàng từng tháng trong năm
            List<Object[]> billByMonth = dashboardService.getOrderLineCountByMonth(0);
            model.addAttribute("billByMonth", billByMonth);
        } catch (Exception e) {
            model.addAttribute("errBillByMonth", "Error Bill By Month");
        }

        try {
            // tông số sản phẩm theo từng danh mục
            List<Object[]> totalProductByCategory = dashboardService.getAmountByCategory();
            model.addAttribute("totalProductByCategory", totalProductByCategory);
        } catch (Exception e) {
            model.addAttribute("errTotalProduct", "Error Total Product");
        }

        try {
            List<Object[]> totalProductSoldByCategory = dashboardService.getCategorySold();
            model.addAttribute("totalProductSoldByCategory", totalProductSoldByCategory);
        } catch (Exception e) {
            model.addAttribute("errTotalProductSold", "Error Total Product Sold");
        }

        try {
            // doanh thu mỗi tháng của năm
            List<Object[]> revenueByMonth = dashboardService.getRevenueByMonth(0);
            model.addAttribute("revenueByMonth", revenueByMonth);
        } catch (Exception e) {
            model.addAttribute("errRevenueByMonth", "Error Revenue By Month");
        }

        return "adminLayout/dashboard";
    }
}
