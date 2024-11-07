package com.project.fashion.controller;

import com.project.fashion.dto.response.CustomerDetailResponse;
import com.project.fashion.dto.response.FilterResponse;
import com.project.fashion.model.Category;
import com.project.fashion.model.Product;
import com.project.fashion.service.implement.CartServiceImplement;
import com.project.fashion.service.implement.CategoryServiceImplement;
import com.project.fashion.service.implement.ProductServiceImplement;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

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
            Category category = categoryServiceImplement.getCategory(categoryId);
            model.addAttribute("categories", category);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Không thể tải sản phẩm. Vui lòng thử lại sau.");
        }
        return "home";
    }

    @GetMapping("/{categoryId}/filter")
    public String homeCategoryByPrice(Model model, @PathVariable Long categoryId, @RequestParam("low") Long low,
            @RequestParam("hight") Long hight) {
        try {
            List<Product> products = productServiceImplement.getProductByCategoryAndByPrice(low, hight, categoryId);
            Category category = categoryServiceImplement.getCategory(categoryId);

            FilterResponse filter = new FilterResponse(category.getName(), products);

            model.addAttribute("categories", filter);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Không thể tải sản phẩm. Vui lòng thử lại sau.");
        }
        return "home";
    }

}
