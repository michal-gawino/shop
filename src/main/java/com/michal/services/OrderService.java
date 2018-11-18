package com.michal.services;

import com.michal.entities.Order;
import com.michal.entities.User;
import java.util.List;

public interface OrderService extends GenericService<Order, Long> {

    List<Order> findByUser(User user);
}
