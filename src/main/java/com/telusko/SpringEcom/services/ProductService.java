package com.telusko.SpringEcom.services;

import com.telusko.SpringEcom.exception.ResourceNotFoundException;
import com.telusko.SpringEcom.models.Product;
import com.telusko.SpringEcom.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author Joesta
 */

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product saveOrUpdateProduct(Product product, MultipartFile imageFile) {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        try {
            product.setImage(imageFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with id " + id)
                );
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> searchByNameOrDescription(String keyword) {
        return productRepository.findByNameContainsIgnoreCaseOrDescriptionContainsIgnoreCase(keyword, keyword);
    }

    public List<Product> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword);
    }

    public List<Product> getAllProductsByIds(List<Long> ids) {
        return productRepository.findAllById(ids);
    }
}
