package com.telusko.SpringEcom.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Joesta
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
@Getter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private String brand;
    private String imageName;
    private String imageType;
    @Lob
    private byte[] image;
    private boolean isProductAvailable;
    private Integer stockQuantity;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date releaseDate;
    private String category;

    public Product(Long id) {
        this.id = id;
    }

}
