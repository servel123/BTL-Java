package com.project.fashion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.fashion.dto.request.CusModifyInfo;
import com.project.fashion.dto.response.CategoryResDTO;
import com.project.fashion.dto.response.CustomerDetailResponse;
import com.project.fashion.model.OrderLine;
import com.project.fashion.service.implement.CategoryServiceImplement;
import com.project.fashion.service.implement.CustomerServiceImplement;
import com.project.fashion.service.implement.OrderLineServiceImplement;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Locale.Category;

@Controller
@RequestMapping("/user")
public class CustomerController {

    @Autowired
    private CustomerServiceImplement customerServiceImplement;
    @Autowired
    private OrderLineServiceImplement orderLineServiceImplement;

    @Autowired
    private CategoryServiceImplement categoryServiceImplement;

    @Autowired
    private Authen authen;

    // get information of customer
    @GetMapping
    public String infoCustomer(Model model) {
        try {
            CustomerDetailResponse data = authen.authen();
            model.addAttribute("info", data);
        } catch (Exception e) {
            model.addAttribute("errorGetInfo", e.getMessage());
            return "redirect:/error";
        }
        try {
            CustomerDetailResponse cus = authen.authen();
            List<OrderLine> orderLines = orderLineServiceImplement.getOrderLinesOfCustomer(cus.getCustomerId());
            model.addAttribute("bills", orderLines);
            List<CategoryResDTO> fashions = categoryServiceImplement.showCategory();
            model.addAttribute("fashions", fashions);
        } catch (Exception e) {
            model.addAttribute("errorBill", e.getMessage());
        }
        return "user";
    }

    // update information of customer
    @PatchMapping
    public String updateCustomer(
            @Valid CusModifyInfo cusRequestDTO,
            RedirectAttributes redirectAttributes) {
        try {
            CustomerDetailResponse cus = authen.authen();
            cusRequestDTO.setCustomerId(cus.getCustomerId());
            customerServiceImplement.updateCustomer(cusRequestDTO);
            redirectAttributes.addFlashAttribute("message", "Cập nhật thành công");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/user";
    }

    // delete
    @DeleteMapping
    public String deleteCustomer(Model model) {
        try {
            CustomerDetailResponse cus = authen.authen();
            customerServiceImplement.deleteCustomer(cus.getCustomerId());
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorDelete", e.getMessage());
            return "redirect:/home";
        }
    }
}
