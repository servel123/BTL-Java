/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.project.fashion.service;

import com.project.fashion.dto.response.ResponseData;
import com.project.fashion.model.Category;
import com.project.fashion.model.Product;
import java.util.*;

/**
 *
 * @author Vu
 */
public interface ProductService {
    List<Product> getProductShowHome(Category category);

    List<List<Product>> getProductShowHome(List<Category> categorys);

    ResponseData<?> getProductByCategoryAndByPrice(Long categoryId, Long priceLow, Long priceHight);

    ResponseData<?> getProductByCategoryAndByPrice(Long categoryId, Long priceHight);

    Product getDetailProduct(Long productId);
}
