package com.project.fashion.service;

import java.util.*;

import com.project.fashion.model.Cart;
import jakarta.servlet.http.HttpSession;

public interface CartService {
    Cart addProductToCart(Long customerId, Long productId, Integer quantity, HttpSession session);

    List<Cart> getCartByListId(List<Long> ids);

    List<Cart> getCartByCustomerId(Long customerId);

    void removeProductFromCart(Long cartId);

    Cart updateProductQuantity(Long cartId, Integer quantity);
    
    Integer getCountProductsInCustomerCart(Long customerId);
}