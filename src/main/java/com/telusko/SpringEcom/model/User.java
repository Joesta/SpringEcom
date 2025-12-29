package com.telusko.SpringEcom.model;

import com.telusko.SpringEcom.enums.Role;
import jakarta.persistence.*;
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
    @Enumerated(EnumType.STRING)
    private Role role;
}
