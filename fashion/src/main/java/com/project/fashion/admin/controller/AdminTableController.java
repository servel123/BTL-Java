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
import com.project.fashion.dto.request.UpdateProductDTO;
import com.project.fashion.model.*;
import com.project.fashion.service.implement.CategoryServiceImplement;
import com.project.fashion.service.implement.CustomerServiceImplement;
//import com.project.fashion.service.implement.PaymentServiceImplement;
import com.project.fashion.service.implement.ProductServiceImplement;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
            List<Category> categories = categoryServiceImplement.getAllCategories();
            List<Product> products = productServiceImplement.getAllProducts();
            model.addAttribute("products", products);
            model.addAttribute("categories", categories);
        } catch (Exception e) {
            model.addAttribute("errProduct", "error get product");
        }
        return "adminLayout/adminProducts";
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

    @PatchMapping("/product/update")
    public String adminUpdateProduct(
            @Valid UpdateProductDTO product, RedirectAttributes redirectAttributes) {
        try {
            productServiceImplement.updateProduct(product);
            redirectAttributes.addFlashAttribute("message", "Sửa sản phẩm thành công");
        } catch (Exception e) {
            log.info(e.toString());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/admin/product";
    }

    @DeleteMapping("/product/{productId}")
    public String adminDeleteProduct(@Valid @PathVariable Long productId, RedirectAttributes redirectAttributes) {
        try {
            productServiceImplement.deleteProduct(productId);
            redirectAttributes.addFlashAttribute("message", "Xóa sản phẩm thành công");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Đã có lỗi xảy ra");
        }
        return "redirect:/admin/product";
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
            redirect.addFlashAttribute("message", "Thêm sản phẩm thành công!");
        } catch (Exception e) {
            redirect.addFlashAttribute("message", "Không thể thêm sản phẩm! Đã xảy ra lỗi");
        }
        return "redirect:/admin/category";
    }

    @PatchMapping("/category")
    public String adminUpdateCategory(@Valid AddCategoryDTO category, RedirectAttributes redirect) {
        try {
            categoryServiceImplement.updateCategory(category);
            redirect.addFlashAttribute("message", "Sửa danh mục thành công");
        } catch (Exception e) {
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
