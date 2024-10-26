package com.project.fashion.service;

import java.util.*;

import com.project.fashion.model.Cart;

public interface CartService {
    Cart addProductToCart(Long customerId, Long productId, Integer quantity);

    List<Cart> getCartByListId(List<Long> ids);

    List<Cart> getCartByCustomerId(Long customerId);

    void removeProductFromCart(Long cartId);

    Cart updateProductQuantity(Long cartId, Integer quantity);

}