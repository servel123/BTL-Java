package com.project.fashion.service.implement;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.fashion.exception.ResourceNotFoundException;
import com.project.fashion.model.Cart;

import com.project.fashion.repository.CartReponsitory;
import com.project.fashion.service.CartService;

@Service
public class CartServiceImplement implements CartService {
    @Autowired
    private CartReponsitory cartReponsitory;

    @Autowired
    private CustomerServiceImplement customerServiceImplement;

    @Autowired
    private ProductServiceImplement productServiceImplement;

    private Cart getCartById(Long cartId) {
        return cartReponsitory.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Not cartId"));
    }

    // add product to cart
    @Override
    public Cart addProductToCart(Long customerId, Long productId, Integer quantity) {
        Cart cart = cartReponsitory.findByProduct_ProductIdAndCustomer_CustomerId(productId, customerId);
        if (cart != null) {
            cart.setQuantity(quantity);
            cart.setTotalPrice();
            cartReponsitory.save(cart);
            return cart;
        } else {
            Cart newcart = Cart.builder()
                    .customer(customerServiceImplement.getCustomerById(customerId))
                    .product(productServiceImplement.getProductById(productId))
                    .quantity(quantity)
                    .build();
            newcart.setTotalPrice();
            cartReponsitory.save(newcart);
            return newcart;
        }
    }

    // get list product by customerId in cart
    @Override
    public List<Cart> getCartByCustomerId(Long customerId) {
        List<Cart> carts = cartReponsitory.findByCustomer_CustomerId(customerId);
        return carts;
    }

    @Override
    public List<Cart> getCartByListId(List<Long> ids) {
        List<Cart> carts = new ArrayList<>();
        for (Long id : ids) {
            carts.add(getCartById(id));
        }
        return carts;
    }

    @Override
    public void removeProductFromCart(Long cartId) {
        cartReponsitory.deleteById(cartId);
    }

    @Override
    public Cart updateProductQuantity(Long cartId, Integer quantity) {
        Cart cart = getCartById(cartId);
        cart.setQuantity(quantity);
        cartReponsitory.save(cart);
        return cart;
    }

}
