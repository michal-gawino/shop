package com.michal.services;

import com.michal.entities.Category;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface CategoryService extends GenericService<Category, Long> {

    Category findByName(String name);
    Category create(Category c, MultipartFile image) throws IOException;

}
