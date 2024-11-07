package com.project.fashion.admin.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.fashion.dto.request.AddCategoryDTO;
import com.project.fashion.dto.request.AddProductDTO;
import com.project.fashion.model.*;
import com.project.fashion.service.implement.CategoryServiceImplement;
import com.project.fashion.service.implement.CustomerServiceImplement;
import com.project.fashion.service.implement.PaymentServiceImplement;
import com.project.fashion.service.implement.ProductServiceImplement;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/table")
public class AdminTableController {

    private CustomerServiceImplement customerServiceImplement;
    private ProductServiceImplement productServiceImplement;
    private CategoryServiceImplement categoryServiceImplement;
    private PaymentServiceImplement paymentServiceImplement;

    // CUSTOMER
    @GetMapping("/customer")
    public String adminTableCustomer(Model model) {
        try {
            List<Customer> users = customerServiceImplement.getAllCustomer();
            model.addAttribute("users", users);
        } catch (Exception e) {
            model.addAttribute("errUser", "error get user");
        }
        return "tableUsers";
    }

    @PatchMapping("/customer/{customerId}")
    public String updateRoleUser(@RequestParam("customerId") Long customerId, @RequestParam("role") String role,
            Model model) {
        try {
            customerServiceImplement.updateRoleCustomer(customerId, role);
        } catch (Exception e) {
            model.addAttribute("message", "Fail");
        }
        return "redirect:/admin/customer";
    }

    @DeleteMapping("/customer")
    public String adminDeleteCustomer(@RequestParam("customerId") Long customerId) {
        customerServiceImplement.deleteCustomer(customerId);
        return "tableUsers";
    }

    // PRODUCT
    @GetMapping("/product")
    public String adminTableProduct(Model model) {
        try {
            List<Product> products = productServiceImplement.getAllProducts();
            model.addAttribute("products", products);
        } catch (Exception e) {
            model.addAttribute("errProduct", "error get product");
        }
        return "tableProduct";
    }

    @GetMapping("/product/{productId}")
    public String adminGetProduct(@Valid @PathVariable Long productId, Model model) {
        try {
            Product product = productServiceImplement.getDetailProduct(productId);
            model.addAttribute("product", product);
            model.addAttribute("fixProduct", new AddProductDTO());
        } catch (Exception e) {
            model.addAttribute("errProduct", "error get product");
        }
        return "fixproduct";
    }

    @PatchMapping("/product/{productId}")
    public String adminUpdateProduct(@Valid @PathVariable Long productId,
            @ModelAttribute("fixProduct") AddProductDTO product, Model model) {
        try {
            productServiceImplement.updateProduct(productId, product);
        } catch (Exception e) {
            model.addAttribute("errProduct", "error get product");
        }
        return "tableProduct";
    }

    @DeleteMapping("/product/{productId}")
    public String adminDeleteProduct(@Valid @PathVariable Long productId, Model model) {
        try {
            productServiceImplement.deleteProduct(productId);
        } catch (Exception e) {
            model.addAttribute("errProduct", "error get product");
        }
        return "tableProduct";
    }

    // CATEGORY
    @GetMapping("/category")
    public String adminTableCategory(Model model) {
        try {
            List<Category> categories = categoryServiceImplement.getAllCategories();
            model.addAttribute("categories", categories);
        } catch (Exception e) {
            model.addAttribute("errProduct", "error get categories");
        }
        return "tableCategory";
    }

    @GetMapping("/category/{categoryId}")
    public String adminGetCategory(@Valid @PathVariable Long categoryId, Model model) {
        try {
            Category category = categoryServiceImplement.getCategory(categoryId);
            model.addAttribute("category", category);
            model.addAttribute("fixCategory", new AddCategoryDTO());
        } catch (Exception e) {
            model.addAttribute("errCategory", "error get category");
        }
        return "fixCategory";
    }

    @PostMapping("/category/new")
    public String adminAddCategor(@ModelAttribute("newCategory") AddCategoryDTO category, RedirectAttributes redirect) {
        try {
            categoryServiceImplement.addCategory(category);
            redirect.addAttribute("Add category successfully!");
        } catch (Exception e) {
            redirect.addAttribute("Add category failed!");
        }
        return "redirect:/admin/category";
    }

    @PatchMapping("/category/{categoryId}")
    public String adminUpdateCategory(@Valid @PathVariable Long categoryId,
            @ModelAttribute("fixCategory") AddCategoryDTO category, Model model) {
        try {
            categoryServiceImplement.updateCategory(category);
        } catch (Exception e) {
            model.addAttribute("errCategory", "error get category");
        }
        return "tableCategory";
    }

    @DeleteMapping("/category/{categoryId}")
    public String adminDeleteCategory(@Valid @PathVariable Long categoryId, Model model) {
        try {
            categoryServiceImplement.deleteCategory(categoryId);
        } catch (Exception e) {
            model.addAttribute("errCategory", "error get category");
        }
        return "tableCategory";
    }

    // PAYMENT
    @GetMapping("/payment")
    public String adminTablePayment(Model model) {
        try {
            List<Payment> payments = paymentServiceImplement.getAllPayment();
            model.addAttribute("payments", payments);
        } catch (Exception e) {
            model.addAttribute("errPayment", "error get payments");
        }
        return "tablePayment";
    }

    @DeleteMapping("/payment/{paymentId}")
    public String adminDeletePayment(@Valid @PathVariable Long paymentId, Model model) {
        try {
            paymentServiceImplement.deletePayment(paymentId);
        } catch (Exception e) {
            model.addAttribute("errPayment", "error get payment");
        }
        return "tablePayment";
    }

}
