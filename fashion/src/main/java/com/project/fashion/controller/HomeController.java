package com.project.fashion.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.project.fashion.model.Product;

@RestController
@RequestMapping("/home")
public class HomeController {
    @GetMapping
    public String home() {
        return "hello world";
    }
}
