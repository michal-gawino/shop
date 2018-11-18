package com.michal.impl;

import com.michal.entities.Order;
import com.michal.entities.User;
import com.michal.repositories.OrderRepository;
import com.michal.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public PagingAndSortingRepository<Order, Long> getRepository() {
        return orderRepository;
    }

    @Override
    public List<Order> findByUser(User user) {
        return orderRepository.findByUser(user);
    }
}
