package com.telusko.SpringEcom.repositories;

import com.telusko.SpringEcom.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Joesta
 */

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
