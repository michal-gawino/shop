package com.michal.services;


import com.michal.entities.User;


public interface UserService extends GenericService<User, Long>  {

    User findByLogin(String login);
}
