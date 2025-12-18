package com.telusko.SpringEcom.services.jwt;

import com.telusko.SpringEcom.dao.UserRepo;
import com.telusko.SpringEcom.model.User;
import com.telusko.SpringEcom.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.SignatureException;

/**
 * @author Joesta
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private UserRepo userRepo;
    private ApplicationContext appContext;


    @Autowired
    public void setAppContext(ApplicationContext appContext) {
        this.appContext = appContext;
    }

    @Autowired
    public void setUserRepo(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Autowired
    public void setJwtService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);

           try {
               username = jwtService.getUsername(token);
           } catch (RuntimeException re) {
               throw new ServletException("Invalid JWT token", re);
           }

            User user = userRepo.findByUsername(username);

            if (user != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = appContext.getBean(UserDetailsServiceImpl.class).loadUserByUsername(username);
                if (jwtService.validateToken(token, userDetails)) {
                    // This creates a Spring Security Authentication object.
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null /*No password needed (JWT already validated)*/, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); //Add extra metadata such as: IP address, SessionID.  Attach request details. Useful for auditing, logging and security check.
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
