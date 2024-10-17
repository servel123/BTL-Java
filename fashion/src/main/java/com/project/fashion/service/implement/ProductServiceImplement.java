package com.project.fashion.service.implement;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.project.fashion.dto.response.ResponseData;
import com.project.fashion.exception.ResourceNotFoundException;
import com.project.fashion.model.Category;
import com.project.fashion.model.Product;
import com.project.fashion.repository.ProductRepository;
import com.project.fashion.service.ProductService;
import java.util.*;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductServiceImplement implements ProductService {

    @Autowired
    private final ProductRepository productRepository;

    protected Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Exists"));
    }

    // get list product by list category
    @Override
    public List<List<Product>> getProductShowHome(List<Category> categorys) {
        List<List<Product>> listProducts = new ArrayList<>();
        for (Category category : categorys) {
            List<Product> products = productRepository.findTop5ByCategory(category);
            if (products.isEmpty()) {
                throw new ResourceNotFoundException("no product");
            } else {
                listProducts.add(products);
            }
        }
        return listProducts;
    }

    // get list product
    @Override
    public List<Product> getProductShowHome(Category category) {
        List<Product> products = productRepository.findTop5ByCategory(category);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("no product");
        }
        return products;
    }

    @Override
    public ResponseData<?> getProductByCategoryAndByPrice(Long categoryId, Long priceLow, Long priceHight) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProductByCategoryAndByPrice'");
    }

    @Override
    public ResponseData<?> getProductByCategoryAndByPrice(Long categoryId, Long priceHight) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProductByCategoryAndByPrice'");
    }

    @Override
    public Product getDetailProduct(Long productId) {
        return getProductById(productId);
    }

}
