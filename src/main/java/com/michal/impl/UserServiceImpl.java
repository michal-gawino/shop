package com.michal.impl;

import com.michal.entities.User;
import com.michal.repositories.UserRepository;
import com.michal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

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
}
