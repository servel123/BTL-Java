package com.project.fashion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;

// import com.project.fashion.dto.request.CusAddProductDTO;
import com.project.fashion.dto.request.CusModifyInfo;
import com.project.fashion.dto.response.CustomerDetailResponse;
import com.project.fashion.dto.response.ResponseData;
//import com.project.fashion.exception.ResourceNotFoundException;
import com.project.fashion.service.implement.CustomerServiceImplement;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("/user")
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerServiceImplement customerServiceImplement;

    // get information of customer
    @GetMapping("/{customerId}")
    public String infoCustomer(@Min(1) @PathVariable Long customerId, Model model) {
        try {
            CustomerDetailResponse data = customerServiceImplement.getInfoCustomer(customerId);
            log.info("this is Object:" + data);
            model.addAttribute("info", data);
            return "user";
        } catch (Exception e) {
            model.addAttribute("errorGetInfo", e.getMessage());
            return "redirect:/error";
        }
    }

    // update information of customer
    @PatchMapping("/{customerId}")
    public ResponseData<CustomerDetailResponse> updateCustomer(
            @Valid @ModelAttribute("customer") CusModifyInfo cusRequestDTO, @PathVariable Long customerId) {
        try {
            cusRequestDTO.setCustomerId(customerId);
            CustomerDetailResponse data = customerServiceImplement.updateCustomer(cusRequestDTO);
            log.info("this is Object:" + data);
            return new ResponseData<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
        } catch (Exception e) {
            return new ResponseData<>(HttpStatus.NOT_FOUND.value(), e.getMessage());
        }
    }

    @DeleteMapping("/{customerId}")
    public String deleteCustomer(@PathVariable Long customerId, Model model) {
        // return new ResponseData<>(HttpStatus.NO_CONTENT.value(),
        // HttpStatus.NO_CONTENT.getReasonPhrase());
        try {
            customerServiceImplement.deleteCustomer(customerId);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorDelete", e.getMessage());
            return "redirect:/home";
        }
    }

}
