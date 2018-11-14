package com.michal.controllers;

import com.michal.entities.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/product")
public class ProductController {

    @PostMapping
    public String create(Product product){
        return "";
    }
}
