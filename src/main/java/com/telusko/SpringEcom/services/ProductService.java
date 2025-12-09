package com.telusko.SpringEcom.services;

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

    public Product saveOrUpdateProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImage(imageFile.getBytes());
        return productRepository.save(product);
    }

    public Product deleteProduct(Product product) {
        productRepository.delete(product);
        return getProductById(product.getId());
    }

//    public Product updateProduct(Product product) {
//        Product productById = getProductById(product.getId());
//        if (productById.getId() > 0L) {
//            return productRepository.save(product);
//        }
//        return productById;
//    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(new Product(-1L));
    }

    public Product deleteProductById(Long id) {
        Product product = getProductById(id);
        productRepository.deleteById(id);
        return product;
    }

    public List<Product> searchByNameOrDescription(String keyword) {
        return productRepository.findByNameContainsIgnoreCaseOrDescriptionContainsIgnoreCase(keyword, keyword);
    }
}
