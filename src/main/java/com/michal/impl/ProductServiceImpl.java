package com.michal.impl;


import com.michal.entities.Category;
import com.michal.entities.Product;
import com.michal.repositories.ProductRepository;
import com.michal.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public PagingAndSortingRepository<Product, Long> getRepository() {
        return productRepository;
    }

    @Override
    public Product findByName(String name) {
        return productRepository.findByName(name);
    }
}
