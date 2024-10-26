package com.project.fashion.controller;

// import com.project.fashion.config.EnvConfig;
import com.project.fashion.model.Category;
import com.project.fashion.service.implement.CategoryServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;

import com.project.fashion.service.implement.CustomerServiceImplement;
import java.util.List;

//import com.project.fashion.model.Product;

//@RestController
@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private CustomerServiceImplement customerServiceImplement;

    @Autowired
    private CategoryServiceImplement categoryServiceImplement;

    @GetMapping
    public String home(Model model) {
        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication != null && authentication.isAuthenticated() ? authentication.getName()
                    : null;
            if (username != null && username.equals("anonymousUser")) {
                username = null;
            } else {
                Long customerId = customerServiceImplement.getCustomerByUsername(username).getCustomerId();
                model.addAttribute("customerId", customerId);
                model.addAttribute("username", username);

            }

        } catch (Exception e) {
            return "redirect:/login";
        }
        try {
            List<Category> categories = categoryServiceImplement.getCategories();
            model.addAttribute("categories", categories);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Không thể tải sản phẩm. Vui lòng thử lại sau.");
        }
        return "home";
    }
}
