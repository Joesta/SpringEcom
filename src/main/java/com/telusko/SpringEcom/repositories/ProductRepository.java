package com.telusko.SpringEcom.repositories;

import com.telusko.SpringEcom.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Joesta
 */

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainsIgnoreCaseOrDescriptionContainsIgnoreCase(String name, String description);
}
