package com.telusko.SpringEcom.dao;


import com.telusko.SpringEcom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Joesta
 */

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
