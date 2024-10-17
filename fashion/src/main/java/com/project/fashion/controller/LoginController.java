/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.fashion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.fashion.dto.request.CreateCustomerDTO;

/**
 *
 * @author Vu
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    @GetMapping
    public String renderFormLogin(Model model) {
        model.addAttribute("customer", new CreateCustomerDTO());
        return "login";
    }
}
