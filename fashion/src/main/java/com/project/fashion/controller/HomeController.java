package com.project.fashion.controller;

import com.project.fashion.dto.response.CustomerDetailResponse;
import com.project.fashion.model.Category;
import com.project.fashion.service.implement.CartServiceImplement;
import com.project.fashion.service.implement.CategoryServiceImplement;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private CategoryServiceImplement categoryServiceImplement;
    
    @Autowired
    private CartServiceImplement cartServiceImplement;
    
    @Autowired
    private Authen authen;

    @GetMapping
    public String home(Model model, HttpSession session) {
        try {
            CustomerDetailResponse user = authen.authen();
            Integer countOfProducts = cartServiceImplement.getCountProductsInCustomerCart(user.getCustomerId());
            session.setAttribute("countProductsInCart", countOfProducts);
            model.addAttribute("user", user);
        } catch (Exception e) {
            model.addAttribute("user", null);
        }

        try {
            List<Category> categories = categoryServiceImplement.getCategories();
            model.addAttribute("categories", categories);
            
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Không thể tải sản phẩm. Vui lòng thử lại sau.");
        }

        return "home";
    }

    @GetMapping("/{categoryId}")
    public String homeCategory(Model model, @PathVariable Long categoryId) {
        try {
            CustomerDetailResponse user = authen.authen();
            model.addAttribute("user", user);
        } catch (Exception e) {
            model.addAttribute("user", null);
        }

        try {
            Category category = categoryServiceImplement.getCategory(categoryId);
            List<Category> categories = new ArrayList<>();
            categories.add(category);
            model.addAttribute("categories", categories);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Không thể tải sản phẩm. Vui lòng thử lại sau.");
        }

        return "home";
    }

}
