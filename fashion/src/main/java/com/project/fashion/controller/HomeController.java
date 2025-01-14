package com.project.fashion.controller;

import com.project.fashion.dto.response.CategoryResDTO;
import com.project.fashion.dto.response.CustomerDetailResponse;
import com.project.fashion.dto.response.FilterResponse;
import com.project.fashion.model.Category;
import com.project.fashion.model.Product;
import com.project.fashion.service.implement.CartServiceImplement;
import com.project.fashion.service.implement.CategoryServiceImplement;
import com.project.fashion.service.implement.ProductServiceImplement;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

import java.time.LocalDate;

@Slf4j
@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private CategoryServiceImplement categoryServiceImplement;

    @Autowired
    private CartServiceImplement cartServiceImplement;

    @Autowired
    private ProductServiceImplement productServiceImplement;

    @Autowired
    private Authen authen;

    @GetMapping

    public String home(Model model, HttpSession session) {
        try {
            CustomerDetailResponse user = authen.authen();
            Integer countOfProducts = cartServiceImplement.getCountProductsInCustomerCart(user.getCustomerId());
            session.setAttribute("countProductsInCart", countOfProducts);
            session.setAttribute("username", user.getUsername());
            model.addAttribute("user", user);
        } catch (Exception e) {
            model.addAttribute("user", null);
        }
        int currentYear = LocalDate.now().getYear();
        session.setAttribute("year", currentYear);
        try {
            List<CategoryResDTO> fashions = categoryServiceImplement.showCategory();
            List<Category> categories = categoryServiceImplement.getCategories();
            model.addAttribute("categories", categories);
            model.addAttribute("fashions", fashions);

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Loaded failed!!!");
        }
        return "home";
    }

    @GetMapping("/{categoryId}")
    public String homeCategory(Model model, @PathVariable Long categoryId) {
        try {
            Category category = categoryServiceImplement.getCategory(categoryId);
            model.addAttribute("categories", category);
            model.addAttribute("categoryId", categoryId);
            List<CategoryResDTO> fashions = categoryServiceImplement.showCategory();
            model.addAttribute("fashions", fashions);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "qui";
    }

    @GetMapping("/filter")
    public String homeCategoryByPrice(Model model, @RequestParam("categoryId") Long categoryId,
            @RequestParam("low") Long low,
            @RequestParam("high") Long high) {
        try {
            List<Product> products = productServiceImplement.getProductByCategoryAndByPrice(low, high, categoryId);
            Category category = categoryServiceImplement.getCategory(categoryId);
            List<CategoryResDTO> fashions = categoryServiceImplement.showCategory();
            model.addAttribute("fashions", fashions);
            FilterResponse filter = new FilterResponse(category.getName(), products);
            model.addAttribute("cate", category);

            model.addAttribute("categories", filter);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "qui";
    }

    @GetMapping("/search")
    public String homeSearch(RedirectAttributes redirect, @RequestParam("search") String keyword, Model model) {
        try {

            List<Product> products = productServiceImplement.findByKeyWord(keyword);
            if (products.isEmpty()) {
                redirect.addFlashAttribute("message_search", "Kết quả tìm kiếm không tồn tại!");

            } else {
                FilterResponse filter = new FilterResponse("Tìm kiếm", products);
                model.addAttribute("categories", filter);
            }
            List<CategoryResDTO> fashions = categoryServiceImplement.showCategory();
            model.addAttribute("fashions", fashions);
        } catch (Exception e) {
            redirect.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "home";
    }

}
