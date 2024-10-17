package com.project.fashion.service.implement;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.fashion.exception.ResourceNotFoundException;
import com.project.fashion.model.Cart;
import com.project.fashion.model.Product;
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
        Cart cart = Cart.builder()
                .customer(customerServiceImplement.getCustomerById(customerId))
                .product(productServiceImplement.getProductById(productId))
                .quantity(quantity)
                .build();
        return cart;
    }

    // get list product by customerId in cart
    @Override
    public List<Product> getCartByCustomerId(Long customerId) {
        List<Cart> carts = cartReponsitory.findByCustomer_CustomerId(customerId);
        return carts.stream()
                .map(Cart::getProduct)
                .collect(Collectors.toList());
    }

    @Override
    public void removeProductFromCart(Long cartId) {
        cartReponsitory.deleteById(cartId);
    }

    @Override
    public Cart updateProductQuantity(Long cartId, Integer quantity) {
        return getCartById(cartId);
    }

}
