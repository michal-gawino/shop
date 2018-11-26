package com.michal.services;


import com.michal.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService extends GenericService<User, Long>  {

    User findByLogin(String login);
    Page<User> getAll(Pageable pageable);
    void createUser(User u);
    User getCurrent();
}
