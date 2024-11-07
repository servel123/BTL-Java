package com.project.fashion.service.implement;

import com.project.fashion.dto.request.AddProductDTO;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.project.fashion.exception.ErrorDataException;
import com.project.fashion.exception.ResourceNotFoundException;
import com.project.fashion.model.Cart;
import com.project.fashion.model.Category;
import com.project.fashion.model.Customer;
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

    public List<Product> getAllProducts() {
        return productRepository.findAll();
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

    // get product by price
    @Override
    public List<Product> getProductByCategoryAndByPrice(Long priceLow, Long priceHight, Long categoryId) {
        List<Product> products = productRepository.findByPriceBetweenAndCategory_CategoryId(priceLow,
                priceHight, categoryId);
        if (products.isEmpty()) {
            String message = "Not product have price between " + priceLow + " and " + priceHight;
            throw new ResourceNotFoundException(message);
        }
        return products;

    }

    // detail product
    @Override
    public Product getDetailProduct(Long productId) {
        return getProductById(productId);
    }

    // get all product by category
    @Override
    public List<Product> getAllProductByCategory(Category category) {
        List<Product> products = productRepository.findAllByCategory(category);
        if (products.size() > 0)
            return products;
        else {
            String message = "Not product by " + category.getName();
            throw new ResourceNotFoundException(message);
        }
    }

    // add product
    @Override
    public Product addProduct(AddProductDTO product) {
        Product pdt = Product.builder()
                .category(product.getCategory())
                .description(product.getDesciption())
                .image(product.getImage())
                .name(product.getName())
                .stock(product.getStock())
                .build();
        productRepository.save(pdt);
        return pdt;
    }

    // delete
    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    // update
    @Override
    public Product updateProduct(Long productId, AddProductDTO product) {
        Product pdt = getProductById(productId);
        if (pdt == null) {
            throw new ResourceNotFoundException("Customer not found with ID: " + productId);
        }
        try {
            if (product.getCategory() != null) {
                pdt.setCategory(product.getCategory());
            }
            if (product.getImage() != null) {
                pdt.setImage(product.getImage());
            }
            if (product.getPrice() != null) {
                pdt.setPrice(product.getPrice());
            }
            if (product.getStock() >= 0) {
                pdt.setStock(product.getStock());
            } else {
                throw new ErrorDataException("Data not valid");
            }
            productRepository.save(pdt);
            return pdt;

        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public void updateStockProduct(Long productId, Integer stock) {
        Product product = getDetailProduct(productId);
        Integer quantity = product.getStock() - stock;
        if (quantity >= 0) {
            product.setStock(quantity);
            productRepository.save(product);
        } else {
            throw new ErrorDataException("Số lượng hàng còn lại không đáp ứng số lượng quý khách yêu cầu !!!");
        }

    }

    @Override
    public void subtractionStock(Customer cus) {
        List<Cart> carts = cus.getCarts();
        for (Cart cart : carts) {
            updateStockProduct(cart.getProduct().getProductId(), cart.getQuantity());
        }

    }

    @Override
    public List<Product> findByKeyWord(String keyword) {
        List<Product> products = productRepository.findByDescriptionContaining(keyword);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("Không có sản phẩm nào!");
        }
        return products;
    }
}
