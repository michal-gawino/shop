package com.michal.controllers;

import com.michal.entities.User;
import com.michal.impl.UserServiceImpl;
import com.michal.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/register")
class RegisterController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserValidator userValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder){
        binder.addValidators(userValidator);
    }

    @GetMapping
    public String getRegisterView() {
        return "register";
    }

    @PostMapping
    public String registerUser(@Valid User user, BindingResult bindingResult, Model model, RedirectAttributes redirectAttrs){
        if(bindingResult.hasFieldErrors()){
            return "register";
        }
        userService.createUser(user);
        redirectAttrs.addFlashAttribute("success", "Registration successful");
        return "redirect:/register";
    }
}
