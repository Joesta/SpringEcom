package com.telusko.SpringEcom.controllers;

import com.telusko.SpringEcom.models.Product;
import com.telusko.SpringEcom.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

/**
 * @author Joesta
 */

@RestController
@RequestMapping("api")
public class ProductController {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("products")
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = productService.getProducts();
        if (!products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product.getId() > 0L) {
            return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        if (product.getId() > 0L) {
            return new ResponseEntity<>(product.getImage(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("product")
    public ResponseEntity<?> saveProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile) {
        Product savedProduct = null;
        try {
            savedProduct =productService.saveProduct(product, imageFile);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (IOException e) {
            return  new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("product")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(product);
        if (updatedProduct.getId() > 0L) {
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("product")
    public ResponseEntity<Product> deleteProduct(@RequestBody Product product) {
        Product deleted =  productService.deleteProduct(product);
        if (deleted.getId() > 0L) {
            return new ResponseEntity<>(deleted, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("product/{id}")
    public ResponseEntity<Product> deleteProductById(@PathVariable Long id) {
        Product product =  productService.deleteProductById(id);
        if (product.getId() > 0L) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("products/{keyword}")
    public ResponseEntity<List<Product>> searchByKeyword(@PathVariable String keyword) {
        List<Product> products = productService.searchByNameOrDescription(keyword);
        if (!products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
