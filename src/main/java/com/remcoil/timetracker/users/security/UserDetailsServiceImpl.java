package com.remcoil.timetracker.users.security;

import com.remcoil.timetracker.users.core.UserNotFoundException;
import com.remcoil.timetracker.users.core.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return userService.getById(UUID.fromString(username));
        } catch (UserNotFoundException e){
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        }
    }
}