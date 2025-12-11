package com.telusko.SpringEcom.services;

import com.telusko.SpringEcom.models.Order;
import com.telusko.SpringEcom.models.OrderItem;
import com.telusko.SpringEcom.models.Product;
import com.telusko.SpringEcom.models.dto.OrderItemRequest;
import com.telusko.SpringEcom.models.dto.OrderItemResponse;
import com.telusko.SpringEcom.models.dto.OrderRequest;
import com.telusko.SpringEcom.models.dto.OrderResponse;
import com.telusko.SpringEcom.repositories.OrderRepository;
import com.telusko.SpringEcom.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Joesta
 */

@Service
public class OrderService {

    private ProductRepository productRepository;

    private OrderRepository orderRepository;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<OrderResponse> getAllOrderResponses() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponse> orderResponse = new ArrayList<>();

        for (Order order : orders) {
            List<OrderItemResponse> orderItemResponses = new ArrayList<>();

            for (OrderItem orderItem : order.getItems()) {
                OrderItemResponse orderItemResponse = new OrderItemResponse(orderItem.getProduct().getName(), orderItem.getQuantity(), orderItem.getTotalPrice());
                orderItemResponses.add(orderItemResponse);
            }

            orderResponse.add(new OrderResponse(order.getOrderId(), order.getCustomerName(), order.getEmail(), order.getStatus(), order.getOrderDate(), orderItemResponses));
        }
        return orderResponse;
    }

    private String generateUniqueOrderId() {
        return "ORD" + UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase(); // advisable to use the entire generate UUID
    }

    @Transactional
    public OrderResponse placeOrder(OrderRequest req) {
        Order order = new Order();

        // generate unique UUID for the order items.
        order.setOrderId(generateUniqueOrderId());
        order.setCustomerName(req.customerName());
        order.setEmail(req.email());
        order.setStatus("PLACED");
        order.setOrderDate(LocalDate.now());

        List<OrderItemRequest> items = req.items();
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequest reqItem : items) {
            Product product = productRepository.findById(reqItem.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.isProductAvailable() && product.getStockQuantity() > 0 && product.getStockQuantity() >= reqItem.quantity()) {
                product.setStockQuantity(product.getStockQuantity() - reqItem.quantity());
                productRepository.save(product);

                OrderItem orderItem = OrderItem.builder()
                        .order(order)
                        .product(product)
                        .quantity(reqItem.quantity())
                        .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(reqItem.quantity())))
                        .build();

                orderItems.add(orderItem);

//                orderItem.setOrder(order);
//                orderItem.setProduct(product);
//                orderItem.setQuantity(reqItem.quantity());
//                orderItem.setTotalPrice();
//                orderItems.add(orderItem);

            }
        }

        if (!orderItems.isEmpty()) {
            order.setItems(orderItems);
            Order savedOrder = orderRepository.save(order);

            List<OrderItemResponse> orderItemResponses = new ArrayList<>();
            for (OrderItem orderItem : savedOrder.getItems()) {
                OrderItemResponse orderItemResponse = new OrderItemResponse(orderItem.getProduct().getName(), orderItem.getQuantity(), orderItem.getTotalPrice());
                orderItemResponses.add(orderItemResponse);
            }
            return new OrderResponse(order.getOrderId(), order.getCustomerName(), order.getEmail(), order.getStatus(), savedOrder.getOrderDate(), orderItemResponses);
        }

        return null;
    }

}
