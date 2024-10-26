package com.project.fashion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.fashion.dto.request.CusModifyInfo;
import com.project.fashion.dto.response.CustomerDetailResponse;
import com.project.fashion.dto.response.ResponseData;
import com.project.fashion.exception.ResourceNotFoundException;
import com.project.fashion.model.OrderLine;
import com.project.fashion.service.implement.CustomerServiceImplement;
import com.project.fashion.service.implement.OrderLineServiceImplement;

import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequestMapping("/user")
public class CustomerController {

    @Autowired
    private CustomerServiceImplement customerServiceImplement;
    @Autowired
    private OrderLineServiceImplement orderLineServiceImplement;

    private CustomerDetailResponse authen() {
        Authentication au = SecurityContextHolder.getContext().getAuthentication();
        Object userDetail = au.getPrincipal();

        if (userDetail instanceof UserDetails) {
            String username = ((UserDetails) userDetail).getUsername();
            CustomerDetailResponse cus = customerServiceImplement.getInfoCustomer(username);
            return cus;
        } else {
            throw new ResourceNotFoundException("Error");
        }

    }

    // get information of customer
    @GetMapping
    public String infoCustomer(Model model) {
        try {
            CustomerDetailResponse data = authen();
            model.addAttribute("info", data);
        } catch (Exception e) {
            model.addAttribute("errorGetInfo", e.getMessage());
            return "redirect:/error";
        }
        try {
            CustomerDetailResponse cus = authen();
            List<OrderLine> orderLines = orderLineServiceImplement.getOrderLinesOfCustomer(cus.getCustomerId());
            model.addAttribute("bills", orderLines);
        } catch (Exception e) {
            model.addAttribute("errorBill", e.getMessage());
        }
        return "user";
    }

    // update information of customer
    @PatchMapping
    public ResponseData<CustomerDetailResponse> updateCustomer(
            @Valid @ModelAttribute("customer") CusModifyInfo cusRequestDTO) {
        try {
            CustomerDetailResponse cus = authen();
            cusRequestDTO.setCustomerId(cus.getCustomerId());
            CustomerDetailResponse data = customerServiceImplement.updateCustomer(cusRequestDTO);
            return new ResponseData<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
        } catch (Exception e) {
            return new ResponseData<>(HttpStatus.NOT_FOUND.value(), e.getMessage());
        }
    }

    // delete
    @DeleteMapping
    public String deleteCustomer(@PathVariable Long customerId, Model model) {
        try {
            CustomerDetailResponse cus = authen();
            customerServiceImplement.deleteCustomer(cus.getCustomerId());
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorDelete", e.getMessage());
            return "redirect:/home";
        }
    }
}
