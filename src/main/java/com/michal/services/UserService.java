package com.michal.services;


import com.michal.entities.User;

import java.util.List;


public interface UserService extends GenericService<User, Long>  {

    User findByLogin(String login);
    List<User> getAll();
}
