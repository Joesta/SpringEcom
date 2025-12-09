package com.telusko.SpringEcom.repositories;

import com.telusko.SpringEcom.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Joesta
 */

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainsIgnoreCaseOrDescriptionContainsIgnoreCase(String name, String description);


    // we can use DSL like above here.
    @Query("SELECT p FROM Product p WHERE " +
    "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
    "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
    "LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchProducts(String keyword);
}
