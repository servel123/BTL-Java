/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.project.fashion.repository;

import com.project.fashion.model.Cart;
import com.project.fashion.model.Customer;
import com.project.fashion.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
/**
 *
 * @author Vu
 */
public interface CartReponsitory extends JpaRepository<Cart, Long> {
    Optional<Cart> findByProductAndCustomer(Product productId, Customer customerId);   
}
