package com.michal.controllers;

import com.michal.impl.CategoryServiceImpl;
import com.michal.impl.ProductServiceImpl;
import com.michal.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private ProductServiceImpl productService;

    @GetMapping("/users")
    public String getUsersView(Model model){
        model.addAttribute("users", userService.getAll());
        return "admin_users";
    }

    @GetMapping("/products")
    public String getProductsView(Model model){
        model.addAttribute("products", productService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "admin_products";
    }
}
