package com.michal.services;

import com.michal.entities.Product;


public interface ProductService extends GenericService<Product, Long> {

    Product findByName(String name);
}
