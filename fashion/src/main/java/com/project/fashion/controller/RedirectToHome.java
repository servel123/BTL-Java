package com.project.fashion.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/")
public class RedirectToHome {
    @GetMapping
    public String redirectToHome() {
        return "redirect:/home";
    }
}
