/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.fashion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.fashion.dto.request.CreateCustomerDTO;

import com.project.fashion.service.implement.CustomerServiceImplement;

import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Vu
 */
@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private CustomerServiceImplement customerServiceImplement;

    @GetMapping
    public String renderFormRegister(Model model) {
        model.addAttribute("customer", new CreateCustomerDTO());
        return "register";
    }

    @PostMapping
    public String register(@Valid @ModelAttribute("customer") CreateCustomerDTO createCustomerDTO,
            BindingResult result,
            Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("error", createCustomerDTO.getEmail());
            return "register";
        }
        try {
            customerServiceImplement.addCustomer(createCustomerDTO);
            redirectAttributes.addFlashAttribute("messageSuccess", "Đăng ký thành công");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", e);
            return "register";
        }

    }
}
