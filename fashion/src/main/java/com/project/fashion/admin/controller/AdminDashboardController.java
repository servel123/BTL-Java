package com.project.fashion.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.fashion.admin.service.DashboardService;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@Controller
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {
    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public String adminDashboard(Model model, @RequestParam("year") Integer year) {
        try {
            // tổng doanh thu
            Long revenue = dashboardService.totalRevenue();
            model.addAttribute("revenue", revenue);
            log.info("\n\n\n" + revenue.toString());
        } catch (Exception e) {
            model.addAttribute("errRevenue", "Error revenue");
            log.info("\n" + e.getMessage());
        }
        // số lượng đơn hàng từng tháng trong năm
        try {
            List<Object[]> billByMonth = dashboardService.getOrderLineCountByMonth(year);

            // ObjectMapper ob = new ObjectMapper();
            // String json = "";
            // try {
            // json = ob.writeValueAsString(billByMonth);
            // model.addAttribute("billByMonth", json);
            // } catch (Exception e) {
            model.addAttribute("billByMonth", billByMonth);
            // }
        } catch (Exception e) {
            model.addAttribute("errBillByMonth", "Error Bill By Month");
            log.info("\n so luong don:  + billByMonth.toString()" + e.getMessage());
        }
        // tông số sản phẩm theo từng danh mục
        try {

            List<Object[]> totalProductByCategory = dashboardService.getAmountByCategory();
            model.addAttribute("totalProductByCategory", totalProductByCategory);

            log.info("\n so luong sp: ");
            // for (Object[] ob : totalProductByCategory) {
            // log.info("\n\n\n" + ob[0].toString() + "-----" + ob[1].toString());
            // }
        } catch (Exception e) {
            model.addAttribute("errTotalProduct", "Error Total Product");
            log.info("\n so luong sp:  + tatoSanpham.toString()" + e.getMessage());
        }

        // tong so san pham da ban theo tung loai
        try {
            List<Object[]> totalProductSoldByCategory = dashboardService.getCategorySold();
            model.addAttribute("totalProductSoldByCategory", totalProductSoldByCategory);
            log.info("\n\n\n so luong da ban: ");
            for (Object[] ob : totalProductSoldByCategory) {
                log.info("\n" + ob[0].toString() + "-----" + ob[1].toString());
            }
        } catch (Exception e) {
            model.addAttribute("errTotalProductSold", "Error Total Product Sold");
            log.info("\n so luong da ban:  + totalProductSold.toString()" + e.getMessage());
        }
        // doanh thu mỗi tháng của năm
        try {

            List<Object[]> revenueByMonth = dashboardService.getRevenueByMonth(year);
            ObjectMapper ob = new ObjectMapper();
            String json = "";
            try {
                json = ob.writeValueAsString(revenueByMonth);
                model.addAttribute("revenueByMonth", json);
            } catch (Exception e) {
                model.addAttribute("revenueByMonth", revenueByMonth);
            }
        } catch (Exception e) {
            model.addAttribute("errRevenueByMonth", "Error Revenue By Month");
            log.info("\n doanh thu moi thang:  + revenueByMonth.toString()" + e.getMessage());
        }
        // Loi nhuan theo nam
        try {
            Long profit = dashboardService.totalProfit(year);
            model.addAttribute("profit", profit);
        } catch (Exception e) {
            model.addAttribute("errorprofit", "Khong lay duoc loi nhuan");
        }
        return "adminLayout/dashboard";
    }
}
