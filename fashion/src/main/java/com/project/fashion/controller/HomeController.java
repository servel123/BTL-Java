package com.project.fashion.controller;

// import com.project.fashion.config.EnvConfig;
import com.project.fashion.model.Category;
import com.project.fashion.model.Product;
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
import com.project.fashion.service.implement.ProductServiceImplement;
import java.util.List;

//import com.project.fashion.model.Product;

//@RestController
@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private CustomerServiceImplement customerServiceImplement;

    @Autowired
    private ProductServiceImplement productServiceImplement;

    @Autowired
    private CategoryServiceImplement categoryServiceImplement;

    // @Autowired
    // private EnvConfig envConfig;

    @SuppressWarnings("null")
    @GetMapping
    public String home(Model model) {
        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication != null && authentication.isAuthenticated() ? authentication.getName()
                    : null;
            if (username.equals("anonymousUser")) {
                username = null;
            } else {
                Long customerId = customerServiceImplement.getCustomerId(username).getCustomerId();
                model.addAttribute("customerId", customerId);
                model.addAttribute("username", username);

            }

        } catch (Exception e) {

        }
        try {
            Category categoryJean = categoryServiceImplement.getCategory(Long.valueOf("1"));
            Category categoryJacket = categoryServiceImplement.getCategory(Long.valueOf("3"));
            List<Product> productsJean = productServiceImplement.getProductShowHome(categoryJean);
            List<Product> productsJacket = productServiceImplement.getProductShowHome(categoryJacket);
            model.addAttribute("jeans", productsJean);
            model.addAttribute("jackets", productsJacket);
            // String listCategoryId = envConfig.getListCategory();
            // String[] categoryString = listCategoryId.split(",");
            // Integer cate = Integer.parseInt(envConfig.getShowQuantity());
            // Long[] categoryIds = new Long[cate];
            // int i = 0;
            // for (String word : categoryString) {
            // categoryIds[i] = Long.parseLong(word);
            // i++;
            // }
            // List<Category> categories =
            // categoryServiceImplement.getCategory(categoryIds);
            // List<List<Product>> products =
            // productServiceImplement.getProductShowHome(categories);
            // model.addAttribute("objPrd", products);

        } catch (Exception e) {

            model.addAttribute("errorMessage", "Không thể tải sản phẩm. Vui lòng thử lại sau.");
        }

        return "home";
    }
}
