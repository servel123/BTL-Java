package com.project.fashion.service.implement;

import com.project.fashion.dto.request.AddProductDTO;
import com.project.fashion.dto.request.UpdateProductDTO;

import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.fashion.exception.ErrorDataException;
import com.project.fashion.exception.ResourceNotFoundException;
import com.project.fashion.model.Cart;
import com.project.fashion.model.Category;
import com.project.fashion.model.Customer;
import com.project.fashion.model.Product;
import com.project.fashion.repository.ProductRepository;
import com.project.fashion.service.ProductService;
import com.project.fashion.util.Path;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImplement implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryServiceImplement categoryServiceImplement;

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Exists"));
    }

    @Override
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
        String path = "";
        if (product.getImage() != null) {
            try {
                MultipartFile image = product.getImage();
                String filename = image.getOriginalFilename();
                if (filename == null) {
                    throw new NullPointerException("File Name Null");
                }
                int dotIndex = filename.lastIndexOf(".");
                log.info("\n\n\n" + image.getOriginalFilename() + "\n\n\n");
                if (dotIndex > 0 && dotIndex < filename.length() - 1) {
                    String ext = filename.substring(dotIndex + 1);
                    String name = filename.substring(0, dotIndex);
                    log.info("\n\n\n2\n\n\n");
                    if (ext.equals("png") || ext.equals("jpg")) {
                        String category = product.getCategory();
                        name = name + LocalDate.now().toString().toLowerCase();
                        path = "/images/" + category + "/" + name + "." + ext;
                        String savePath = Path.path + path;
                        log.info("\n\n\n3\n\n\n");
                        File file = new File(savePath);
                        image.transferTo(file);
                        log.info("\n\n\n4\n\n\n");
                    }
                } else {
                    throw new InvalidFileNameException(filename, "Tep khong dung dinh dang");
                }
            } catch (NullPointerException e) {
                log.info("\n\n" + e.getMessage() + "\n\n");
            } catch (Exception e) {
                log.info("\n\n" + e + "\n\n");
            }
        }
        Product pdt = Product.builder()
                .category(categoryServiceImplement.getCategoryByName(product.getCategory()))
                .description(product.getDescription())
                .image(path)
                .name(product.getName())
                .stock(product.getStock())
                .originalprice(product.getOriginalprice())
                .price(product.getPrice())
                .build();
        productRepository.save(pdt);
        return pdt;
    }

    // update
    @Override
    public Product updateProduct(UpdateProductDTO product) {
        Product pdt = getProductById(product.getId());
        log.info("\n\n\n" + product.getCategory() + "\n\n\n");
        if (pdt == null) {
            throw new ResourceNotFoundException("Customer not found with ID: " + product.getId());
        }

        if (product.getImage() != null) {
            try {
                MultipartFile image = product.getImage();
                String filename = image.getOriginalFilename();
                if (filename == null) {
                    throw new NullPointerException("File Name Null");
                }
                int dotIndex = filename.lastIndexOf(".");
                log.info("\n\n\n" + image.getOriginalFilename() + "\n\n\n");
                if (dotIndex > 0 && dotIndex < filename.length() - 1) {
                    String ext = filename.substring(dotIndex + 1);
                    String name = filename.substring(0, dotIndex);
                    log.info("\n\n\n2\n\n\n");
                    if (ext.equals("png") || ext.equals("jpg")) {
                        String category = product.getCategory();
                        name = name + LocalDate.now().toString().toLowerCase();
                        String path = "/images/" + category + "/" + name + "." + ext;
                        String savePath = Path.path + path;
                        String pathFileOld = Path.path + pdt.getImage();
                        log.info("\n\n\n3\n\n\n");
                        File file = new File(savePath);
                        image.transferTo(file);
                        pdt.setImage(path);
                        log.info("\n\n\n4\n\n\n");
                        File fileOld = new File(pathFileOld);
                        if (fileOld.exists() && fileOld.delete()) {
                            log.info("\n\n\n5\n\n\n");
                        }
                        log.info("\n\n\n6\n\n\n");
                    }
                } else {
                    throw new InvalidFileNameException(filename, "Tep khong dung dinh dang");
                }
            } catch (Exception e) {

            }
        }

        try {
            if (product.getCategory() != null) {
                pdt.setCategory(categoryServiceImplement.getCategoryByName(product.getCategory()));
            }
            if (product.getPrice() != null) {
                pdt.setPrice(product.getPrice());
            }
            if (product.getDescription() != null) {
                pdt.setDescription(product.getDescription());
            }
            if (product.getOriginalprice() != null) {
                pdt.setPrice(product.getOriginalprice());
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

    // delete
    @Override
    public void deleteProduct(Long productId) {
        try {

            Product product = getProductById(productId);
            productRepository.deleteById(productId);
            String pathFileOld = Path.path + product.getImage();
            File fileOld = new File(pathFileOld);
            if (fileOld.exists() && fileOld.delete()) {
                log.info("\n\n\n5\n\n\n");
            }
            log.info("\n\n\n6\n\n\n");
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
