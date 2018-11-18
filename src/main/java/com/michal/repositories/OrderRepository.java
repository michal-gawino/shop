package com.michal.repositories;

import com.michal.entities.Order;
import com.michal.entities.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    List<Order> findByUser(User user);
}
