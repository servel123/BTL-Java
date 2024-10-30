/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.project.fashion.service;

import com.project.fashion.dto.request.AddProductDTO;
import com.project.fashion.model.Category;
import com.project.fashion.model.Product;
import java.util.*;

/**
 *
 * @author Vu
 */
public interface ProductService {
    // Add product
    Product addProduct(AddProductDTO product);

    // GET
    List<Product> getProductShowHome(Category category);

    List<List<Product>> getProductShowHome(List<Category> category);

    List<Product> getProductByCategoryAndByPrice(Long categoryId, Long priceLow, Long priceHight);

    Product getDetailProduct(Long productId);

    List<Product> getAllProductByCategory(Category category);

    // delete
    void deleteProduct(Long productId);

    // update
    Product updateProduct(Long productId, AddProductDTO pdt);

}
