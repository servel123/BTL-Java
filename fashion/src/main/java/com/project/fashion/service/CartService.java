package com.project.fashion.service;

import java.util.*;

import com.project.fashion.model.Cart;
import com.project.fashion.model.Product;

public interface CartService {
    Cart addProductToCart(Long customerId, Long productId, Integer quantity);

    List<Product> getCartByCustomerId(Long customerId);

    void removeProductFromCart(Long cartId);

    Cart updateProductQuantity(Long cartId, Integer quantity);

}