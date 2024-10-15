/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.project.fashion.repository;

import com.project.fashion.model.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Vu
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByCategory_CategoryId(Long categoryId);
}
