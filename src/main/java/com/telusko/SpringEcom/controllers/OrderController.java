package com.telusko.SpringEcom.controllers;

import com.telusko.SpringEcom.models.dto.OrderRequest;
import com.telusko.SpringEcom.models.dto.OrderResponse;
import com.telusko.SpringEcom.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Joesta
 */

@CrossOrigin(origins = "http://localhost:5173")
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
        OrderResponse orderResponse = orderService.placeOrder(orderRequest);
        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }

    @GetMapping("orders")
    public ResponseEntity<List<OrderResponse>> getAllOrder() {
        List<OrderResponse> orderResponses = orderService.getAllOrderResponses();
        return new ResponseEntity<>(orderResponses, HttpStatus.OK);
//        if (!orderResponses.isEmpty())
//            return new ResponseEntity<>(orderResponses, HttpStatus.OK);
//        else
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
