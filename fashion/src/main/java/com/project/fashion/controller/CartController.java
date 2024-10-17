package com.project.fashion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

import com.project.fashion.model.Cart;
import com.project.fashion.model.Product;
import com.project.fashion.service.implement.CartServiceImplement;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/cart")
public class CartController {

    @Autowired
    private CartServiceImplement cartServiceImplement;

    @GetMapping("/{customerId}")
    public String renderShowCart(@Valid @PathVariable Long customerId, Model model) {
        System.out.println(customerId);
        try {
            List<Product> productOfUser = cartServiceImplement.getCartByCustomerId(customerId);
            model.addAttribute("pOU", productOfUser);

        } catch (Exception e) {
            model.addAttribute("message", "Not Product");
        }
        return "cart";
    }

    // delete product
    @DeleteMapping
    public String deleteProduct(@RequestParam("cartId") Long cartId,
            Model model) {
        try {
            cartServiceImplement.removeProductFromCart(cartId);
            ;
            model.addAttribute("message", "Delete Successfully");
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        return "cart";
    }

    // partial update
    @PatchMapping
    public String updateProduct(@RequestParam("cartId") Long cartId,
            @RequestParam("cartId") Integer quantity,
            Model model) {
        try {
            Cart cart = cartServiceImplement.updateProductQuantity(cartId, quantity);
            model.addAttribute("product", cart);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        return "cart";
    }
}
