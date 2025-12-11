package com.telusko.SpringEcom.models.dto;

import java.math.BigDecimal;

/**
 * @author Joesta
 */
public record OrderItemRequest(Long productId, Integer quantity, BigDecimal price) { }
