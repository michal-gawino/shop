package com.michal.controllers;

import com.michal.entities.Category;
import com.michal.impl.CategoryServiceImpl;
import com.michal.util.FileManager;
import com.michal.validators.CategoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

@Controller
@RequestMapping(value = "/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private FileManager fileManager;

    @Autowired
    private CategoryValidator categoryValidator;

    @Autowired
    private MessageSource messageSource;

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

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String create(@Valid Category category, MultipartFile image, RedirectAttributes redirectAttributes)
            throws IOException {
        Category c = categoryService.create(category, image);
        if(c == null){
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage("file.format.invalid", null, Locale.ENGLISH));
        }
        return "redirect:/category";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public String update(@Valid @PathVariable("id") Category category, Category newCategory, MultipartFile image,
                         RedirectAttributes redirectAttributes) throws IOException {
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
                }else{
                    redirectAttributes.addFlashAttribute("error",
                            messageSource.getMessage("file.format.invalid", null, Locale.ENGLISH));
                }
            }
            categoryService.save(category);
        }
        return "redirect:/category";
    }
}
