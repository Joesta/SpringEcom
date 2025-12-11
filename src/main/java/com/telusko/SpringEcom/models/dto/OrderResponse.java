package com.telusko.SpringEcom.models.dto;

import java.util.List;

/**
 * @author Joesta
 */
public record OrderResponse(String orderId, String customerName, String email, String status, java.time.LocalDate orderDate, List<OrderItemResponse> items) { }
