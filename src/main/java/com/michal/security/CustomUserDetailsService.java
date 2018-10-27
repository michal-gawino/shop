package com.michal.security;

import com.michal.entities.User;
import com.michal.impl.UserServiceImpl;
import com.michal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService extends User implements UserDetailsService {

    @Autowired
    UserServiceImpl userService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userService.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException(login);
        }
        return new CustomUserDetails(user);
    }
}
