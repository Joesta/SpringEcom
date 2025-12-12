package com.telusko.SpringEcom.controllers;

import com.telusko.SpringEcom.model.User;
import com.telusko.SpringEcom.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Joesta
 */

@RestController
@RequestMapping("api")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public ResponseEntity<User> register(@RequestBody User user) {
       User newUser = userService.registerUser(user);
       return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
