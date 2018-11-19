package com.michal.controllers;

import com.michal.entities.Product;
import com.michal.impl.CategoryServiceImpl;
import com.michal.impl.ProductServiceImpl;
import com.michal.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
    public String getUsersView(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "size", defaultValue = "10") int pageSize){
        model.addAttribute("usersPage", userService.getAll(new PageRequest(page - 1 , pageSize)));
        return "admin_users";
    }

    @GetMapping("/products")
    public String getProductsView(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int pageSize){
        model.addAttribute("productsPage", productService.findAllPaginated(new PageRequest(page - 1, pageSize)));
        model.addAttribute("categories", categoryService.findAll());
        return "admin_products";
    }
}
