package com.telusko.SpringEcom.controllers;


import com.telusko.SpringEcom.models.dto.LoginRequest;
import com.telusko.SpringEcom.models.dto.LoginResponse;
import com.telusko.SpringEcom.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Joesta
 */

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }
}
