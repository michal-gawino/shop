package com.michal.controllers;

import com.michal.entities.Category;
import com.michal.impl.CategoryServiceImpl;
import com.michal.services.CategoryService;
import com.michal.util.FileManager;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping(value = "/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private FileManager fileManager;

    @GetMapping
    public String getCategoryView(Model model) throws IOException {
        model.addAttribute("categories", categoryService.findAll());
        return "category";
    }

    @PostMapping
    public String create(Category category, MultipartFile image, Model model) throws IOException {
        BufferedImage img = null;
        try{
            img = ImageIO.read(image.getInputStream());
        } catch (IOException e) {

        }
        if(img != null){
            category.setFilename(image.getOriginalFilename());
            Category c = categoryService.save(category);
            File categoryDir = fileManager.getOrCreateCategoryDirectory(c);
            File imgDest = new File(categoryDir, image.getOriginalFilename());
            image.transferTo(imgDest);
        }
        return "redirect:/category";
    }

    @DeleteMapping(value = "/{idi}")
    public void delete(@PathVariable("idi") Category category) throws IOException {
        if(category != null){
            categoryService.delete(category);
            FileUtils.deleteDirectory(fileManager.getOrCreateCategoryDirectory(category));
        }
    }
}
