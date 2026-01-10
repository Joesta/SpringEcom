package com.telusko.SpringEcom.services;

import com.telusko.SpringEcom.model.UserPrincipal;
import com.telusko.SpringEcom.models.dto.LoginRequest;
import com.telusko.SpringEcom.models.dto.LoginResponse;
import com.telusko.SpringEcom.services.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * @author Joesta
 */

@Service
public class AuthService {
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @Autowired
    public void setJwtService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public LoginResponse login(LoginRequest req) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.username(), req.password()));
        LoginResponse loginResponse = null;

        if (authenticate.isAuthenticated()) {
            UserPrincipal principal = (UserPrincipal) authenticate.getPrincipal();

            if (principal != null) {
                String token = jwtService.generateToken(req.username());
                Long userId = principal.getId();
                loginResponse = new LoginResponse(userId, token);
            }
        }

        return loginResponse;
    }
}
