package com.telusko.SpringEcom.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @author Joesta
 */

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    private Long id;
    private String username;
    private String password;
}
