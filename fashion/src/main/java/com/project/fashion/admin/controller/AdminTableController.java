package com.project.fashion.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.fashion.dto.request.AddCategoryDTO;
import com.project.fashion.dto.request.AddProductDTO;
import com.project.fashion.dto.request.AdminModifyInfoDTO;
import com.project.fashion.model.*;
import com.project.fashion.service.implement.CategoryServiceImplement;
import com.project.fashion.service.implement.CustomerServiceImplement;
//import com.project.fashion.service.implement.PaymentServiceImplement;
import com.project.fashion.service.implement.ProductServiceImplement;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminTableController {

    @Autowired
    private CustomerServiceImplement customerServiceImplement;
    @Autowired
    private ProductServiceImplement productServiceImplement;
    @Autowired
    private CategoryServiceImplement categoryServiceImplement;
    // private PaymentServiceImplement paymentServiceImplement;

    // CUSTOMER
    @GetMapping("/customer")
    public String adminTableCustomer(Model model) {
        try {
            List<Customer> users = customerServiceImplement.getAllCustomer();
            model.addAttribute("users", users);
        } catch (Exception e) {
            model.addAttribute("errUser", "error get user");
        }
        return "adminLayout/adminUsers";
    }

    @PatchMapping("/customer/{customerId}")
    public String updateRoleUser(@Valid AdminModifyInfoDTO admin,
            RedirectAttributes redirectAttributes) {
        try {
            customerServiceImplement.updateRoleCustomer(admin.getId(), admin.getRole());
            redirectAttributes.addFlashAttribute("message", "Sửa đổi thành công");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Sửa đổi thất bại");
        }
        return "redirect:/admin/customer";
    }

    @DeleteMapping("/customer")
    public String adminDeleteCustomer(@RequestParam("customerId") Long customerId,
            RedirectAttributes redirectAttributes) {
        customerServiceImplement.deleteCustomer(customerId);
        redirectAttributes.addFlashAttribute("message", "Xóa tài khoản thành công");
        return "redirect:/admin/customer";
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
        } catch (Exception e) {
            model.addAttribute("errProduct", "error get product");
        }
        return "fixproduct";
    }

    @PostMapping("/product/new")
    public String adminAddProduct(@Valid AddProductDTO product, RedirectAttributes redirect) {
        try {
            productServiceImplement.addProduct(product);
            redirect.addAttribute("product_message", " Thêm sản phẩm thành công!");

        } catch (Exception e) {
            redirect.addAttribute("product_message", " Thêm sản phẩm không thành công!");
        }
        return "redirect:/admin/product";

    }

    @PatchMapping("/product")
    public String adminUpdateProduct(
            @Valid AddProductDTO product, Model model) {
        try {
            productServiceImplement.updateProduct(product);
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
        return "adminLayout/adminCategories";
    }

    @PostMapping("/category/new")
    public String adminAddCategor(@Valid AddCategoryDTO category, RedirectAttributes redirect) {
        try {
            categoryServiceImplement.addCategory(category);
            redirect.addAttribute("Add category successfully!");
        } catch (Exception e) {
            redirect.addAttribute("Add category failed!");
        }
        return "redirect:/admin/category";
    }

    @PatchMapping("/category")
    public String adminUpdateCategory(@Valid AddCategoryDTO category, Model model, RedirectAttributes redirect) {
        try {
            categoryServiceImplement.updateCategory(category);
            redirect.addFlashAttribute("message", "Sửa danh mục thành công");
        } catch (Exception e) {
            model.addAttribute("errCategory", "error get category");
            redirect.addFlashAttribute("message", "Đã có lỗi xảy ra");
        }
        return "redirect:/admin/category";
    }

    @DeleteMapping("/category/{categoryId}")
    public String adminDeleteCategory(@Valid @PathVariable Long categoryId, RedirectAttributes redirectAttributes) {
        try {
            categoryServiceImplement.deleteCategory(categoryId);
            redirectAttributes.addFlashAttribute("message", "Xóa thành công");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Không thể xóa danh mục này");
        }
        return "redirect:/admin/category";
    }

    // PAYMENT
    // @GetMapping("/payment")
    // public String adminTablePayment(Model model) {
    // try {
    // List<Payment> payments = paymentServiceImplement.getAllPayment();
    // model.addAttribute("payments", payments);
    // } catch (Exception e) {
    // model.addAttribute("errPayment", "error get payments");
    // }
    // return "tablePayment";
    // }

    // @DeleteMapping("/payment/{paymentId}")
    // public String adminDeletePayment(@Valid @PathVariable Long paymentId, Model
    // model) {
    // try {
    // paymentServiceImplement.deletePayment(paymentId);
    // } catch (Exception e) {
    // model.addAttribute("errPayment", "error get payment");
    // }
    // return "tablePayment";
    // }

}
