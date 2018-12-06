package com.michal.impl;

import com.michal.entities.User;
import com.michal.enumerated.UserRole;
import com.michal.repositories.UserRepository;
import com.michal.security.CustomUserDetails;
import com.michal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public PagingAndSortingRepository<User, Long> getRepository() {
        return userRepository;
    }

    @Override
    public Page<User> getAll(Pageable pageable) {
        return userRepository.getAll(pageable);
    }

    @Override
    public User createUser(User u) {
        u.setPassword(encoder.encode(u.getPassword()));
        u.setRole(UserRole.CUSTOMER);
        return userRepository.save(u);
    }

    @Override
    public User getCurrent() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }


}
