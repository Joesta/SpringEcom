package com.telusko.SpringEcom.controllers;

import com.telusko.SpringEcom.models.dto.OrderRequest;
import com.telusko.SpringEcom.models.dto.OrderResponse;
import com.telusko.SpringEcom.services.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Joesta
 */

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("api")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("orders/place")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest) {
        return new ResponseEntity<>(orderService.placeOrder(orderRequest), HttpStatus.CREATED);
    }

    @GetMapping("orders")
    public ResponseEntity<List<OrderResponse>> getAllOrder() {
        return new ResponseEntity<>(orderService.getAllOrderResponses(), HttpStatus.OK);
    }

    // @Todo: remove this. Only for testing purpose and learning.
    @GetMapping("csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }
}
