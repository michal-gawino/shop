package com.michal.impl;


import com.michal.entities.Category;
import com.michal.repositories.CategoryRepository;
import com.michal.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public PagingAndSortingRepository<Category, Long> getRepository() {
        return categoryRepository;
    }
}
