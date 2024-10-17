/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.project.fashion.repository;

import com.project.fashion.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

/**
 *
 * @author Vu
 */
public interface CartReponsitory extends JpaRepository<Cart, Long> {
    List<Cart> findByCustomer_CustomerId(Long customerId);
}
