package com.michal.controllers;

import com.michal.entities.Category;
import com.michal.impl.CategoryServiceImpl;
import com.michal.util.FileManager;
import com.michal.validators.CategoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping(value = "/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private FileManager fileManager;

    @Autowired
    private CategoryValidator categoryValidator;

    @InitBinder
    protected void initCategoryBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(categoryValidator);
    }

    @GetMapping
    public String getCategoryView(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "category";
    }

    @GetMapping(value = "/{id}/products")
    public String getCategoryProductsView(@PathVariable("id") Category category, Model model) {
        model.addAttribute("products", category.getProducts());
        return "product";
    }

    @PostMapping
    public String create(@Valid Category category, MultipartFile image) throws IOException {
        BufferedImage img = ImageIO.read(image.getInputStream());
        if(img != null){
            category.setFilename(image.getOriginalFilename());
            Category c = categoryService.save(category);
            File categoryDir = fileManager.getOrCreateCategoryDirectory(c);
            File imgDest = new File(categoryDir, image.getOriginalFilename());
            image.transferTo(imgDest);
        }
        return "redirect:/category";
    }

    @PutMapping("/{id}")
    public String update(@Valid @PathVariable("id") Category category, Category newCategory, MultipartFile image) throws IOException {
        if(category != null){
            if(newCategory.getName() != null){
                category.setName(newCategory.getName());
            }
            if(image != null){
                BufferedImage img = ImageIO.read(image.getInputStream());
                if(img != null){
                    Path categoryImage = fileManager.getCategoryImagePath(category);
                    Files.delete(categoryImage);
                    File imgDest = Paths.get(categoryImage.getParent().toString(), image.getOriginalFilename()).toFile();
                    image.transferTo(imgDest);
                    category.setFilename(image.getOriginalFilename());
                }
            }
            categoryService.save(category);
        }
        return "redirect:/category";
    }
}
