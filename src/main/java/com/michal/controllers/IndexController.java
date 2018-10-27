package com.michal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/")
public class IndexController {

    @GetMapping
    public String index(){
        return "index";
    }

}
