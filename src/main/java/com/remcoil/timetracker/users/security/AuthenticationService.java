package com.remcoil.timetracker.users.security;

import com.remcoil.timetracker.users.core.User;
import com.remcoil.timetracker.users.core.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthenticationService(UserService userService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public Jwt authenticateUser(String id, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(id, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String token = jwtUtils.generateJwtToken(user);
        return new Jwt(token);
    }

    public Jwt registerUser(User user) {
        User savedUser = userService.save(user);
        String token = jwtUtils.generateJwtToken(savedUser);
        return new Jwt(token);
    }

    public List<User> getAll() {
        return userService.getAll();
    }
}
