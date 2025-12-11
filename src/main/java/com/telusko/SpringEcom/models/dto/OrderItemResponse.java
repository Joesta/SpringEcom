package com.telusko.SpringEcom.models.dto;

import java.math.BigDecimal;

/**
 * @author Joesta
 */
public record OrderItemResponse(String productName, Integer quantity, BigDecimal totalPrice) { }
