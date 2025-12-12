package com.telusko.SpringEcom.services;

import com.telusko.SpringEcom.model.User;
import com.telusko.SpringEcom.model.UserPrincipal;
import com.telusko.SpringEcom.dao.UserRepo;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Joesta
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(@Nonnull String username) throws UsernameNotFoundException {

        System.out.println("Username: " + username);
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username not found with username: " + username);
        }

        return new UserPrincipal(user);
    }
}
