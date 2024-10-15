/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.project.fashion.service;

import com.project.fashion.dto.response.ProductDetailResponse;
import com.project.fashion.dto.response.ResponseData;

/**
 *
 * @author Vu
 */
public interface ProductService {
    ResponseData<?> getProductShowHome();

    ResponseData<?> getProductByCategoryAndByPrice(Long categoryId, Long priceLow, Long priceHight);

    ResponseData<?> getProductByCategoryAndByPrice(Long categoryId, Long priceHight);

    ProductDetailResponse getDetailProduct(Long productId);
}
