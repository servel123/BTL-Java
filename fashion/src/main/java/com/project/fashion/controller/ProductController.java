package com.project.fashion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.fashion.dto.response.CategoryResDTO;
import com.project.fashion.dto.response.CustomerDetailResponse;

import com.project.fashion.model.Product;
import com.project.fashion.service.implement.CartServiceImplement;
import com.project.fashion.service.implement.CategoryServiceImplement;
import com.project.fashion.service.implement.CustomerServiceImplement;
import com.project.fashion.service.implement.ProductServiceImplement;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductServiceImplement productServiceImplement;

    @Autowired
    private CartServiceImplement cartServiceImplement;

    @Autowired
    private CategoryServiceImplement categoryServiceImplement;

    @Autowired
    CustomerServiceImplement customerServiceImplement;

    @Autowired
    private Authen authen;

    // detail product
    @GetMapping
    public String detailProduct(@RequestParam("productId") Long productId,
            Model model) {
        try {
            Product product = productServiceImplement.getDetailProduct(productId);
            List<Product> relatedProducts = productServiceImplement.getAllProductByCategory(product.getCategory());
            model.addAttribute("product", product);
            model.addAttribute("relatedProducts", relatedProducts);
            List<CategoryResDTO> fashions = categoryServiceImplement.showCategory();
            model.addAttribute("fashions", fashions);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        return "product";
    }

    // add product to cart
    @PostMapping
    public String addProduct(
            @RequestParam("productId") Long productId,
            @RequestParam("quantity") Integer quantity,
            HttpSession session,
            Model model) {
        try {
            CustomerDetailResponse cus = authen.authen();
            cartServiceImplement.addProductToCart(cus.getCustomerId(), productId, quantity);
            Product product = productServiceImplement.getDetailProduct(productId);
            List<Product> relatedProducts = productServiceImplement.getAllProductByCategory(product.getCategory());
            model.addAttribute("message", "Thêm sản phẩm thành công");
            model.addAttribute("product", product);
            model.addAttribute("relatedProducts", relatedProducts);
            Integer countOfProducts = cartServiceImplement.getCountProductsInCustomerCart(cus.getCustomerId());
            session.setAttribute("countProductsInCart", countOfProducts);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        return "redirect:/product?productId=" + productId;
    }
}
