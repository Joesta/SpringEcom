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
        Product saveProduct = null;
        try {
            saveProduct =productService.saveOrUpdateProduct(product, imageFile);
            return new ResponseEntity<>(saveProduct, HttpStatus.CREATED);
        } catch (IOException e) {
            return  new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("product")
    public ResponseEntity<?> updateProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile) {
        Product updateProduct;
        try {
            updateProduct = productService.getProductById(product.getId());
            if (updateProduct.getId() > 0L) {
                updateProduct = productService.saveOrUpdateProduct(product, imageFile);
                return new ResponseEntity<>(updateProduct, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
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

    // search version 2
    @GetMapping("products/keyword")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        List<Product> products = productService.searchProducts(keyword);
        if (!products.isEmpty())
            return new ResponseEntity<>(products, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
