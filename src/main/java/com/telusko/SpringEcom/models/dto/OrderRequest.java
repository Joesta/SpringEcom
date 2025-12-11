package com.telusko.SpringEcom.models.dto;

import java.util.List;

/**
 * @author Joesta
 */
public record OrderRequest(String customerName, String email, List<OrderItemRequest> items) {}
