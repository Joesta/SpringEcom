package com.telusko.SpringEcom.controllers;

import com.telusko.SpringEcom.models.Product;
import com.telusko.SpringEcom.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * @author Joesta
 */

//@CrossOrigin(origins = "http://localhost:5173")
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
        return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
    }

    @GetMapping("product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    @GetMapping("product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        return new ResponseEntity<>(product.getImage(), HttpStatus.OK);
    }

    @PostMapping("product")
    public ResponseEntity<?> saveProduct(@RequestPart("product") Product product, @RequestPart("imageFile") MultipartFile imageFile) {
        return new ResponseEntity<>(productService.saveOrUpdateProduct(product, imageFile), HttpStatus.CREATED);
    }

    @PutMapping("product")
    public ResponseEntity<?> updateProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile) {
        return new ResponseEntity<>(productService.saveOrUpdateProduct(product, imageFile), HttpStatus.OK);
    }

    @DeleteMapping("product/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "products/{keyword}")
    public ResponseEntity<List<Product>> searchByKeyword(@PathVariable String keyword) {
        return ResponseEntity.ok(productService.searchProducts(keyword));
    }

    @GetMapping(value = "products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        return ResponseEntity.ok(productService.searchProducts(keyword));
    }
}
