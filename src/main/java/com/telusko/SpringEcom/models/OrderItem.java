package com.telusko.SpringEcom.models;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

/**
 * @author Joesta
 */

@Entity
@Builder
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;
    @ManyToOne
    private Product product;
    private Integer quantity;
    private BigDecimal totalPrice;
}
