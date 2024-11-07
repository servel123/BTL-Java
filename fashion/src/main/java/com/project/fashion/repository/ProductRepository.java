/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.project.fashion.repository;

import com.project.fashion.model.Category;
import com.project.fashion.model.Product;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Vu
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findTop5ByCategory(Category category);

    List<Product> findAllByCategory(Category category);

    List<Product> findByPriceBetweenAndCategory_CategoryId(Long low, Long High, Long category);

    List<Product> findByDescriptionContaining(String keyword);
}
