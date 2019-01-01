package com.michal.impl;


import com.michal.entities.Category;
import com.michal.repositories.CategoryRepository;
import com.michal.services.CategoryService;
import com.michal.util.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FileManager fileManager;

    @Override
    public PagingAndSortingRepository<Category, Long> getRepository() {
        return categoryRepository;
    }

    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Category create(Category category, MultipartFile image) throws IOException {
        BufferedImage img = ImageIO.read(image.getInputStream());
        Category c = null;
        if(img != null){
            category.setFilename(image.getOriginalFilename());
            c = save(category);
            File categoryDir = fileManager.getOrCreateCategoryDirectory(c);
            File imgDest = new File(categoryDir, image.getOriginalFilename());
            image.transferTo(imgDest);

        }
        return c;
    }
}
