package com.project.fashion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import com.project.fashion.dto.response.CustomerDetailResponse;
import com.project.fashion.exception.ResourceNotFoundException;
import com.project.fashion.service.implement.CustomerServiceImplement;

@Controller
public class Authen {
    @Autowired
    CustomerServiceImplement customerServiceImplement;

    public CustomerDetailResponse authen() {
        Authentication au = SecurityContextHolder.getContext().getAuthentication();
        Object userDetail = au.getPrincipal();

        if (userDetail instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();
            CustomerDetailResponse cus = customerServiceImplement.getInfoCustomer(username);
            return cus;
        } else {
            throw new ResourceNotFoundException("Error");
        }

    }
}