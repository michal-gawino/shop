package com.michal.controllers;

import com.michal.entities.User;
import com.michal.enumerated.UserRole;
import com.michal.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping(value = "/register")
class RegisterController {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping
    public String register() {
        return "register";
    }


    @PostMapping
    public String create(User user, Model model, RedirectAttributes redirectAttrs){
        User u = userService.findByLogin(user.getLogin());
        if(u != null){
            model.addAttribute("user_exists", "User already exists");
            return "register";
        }else{
            user.setPassword(encoder.encode(user.getPassword()));
            user.setRole(UserRole.CUSTOMER);
            userService.save(user);
            redirectAttrs.addFlashAttribute("success", "Registration successful");
        }
        return "redirect:/register";
    }
}
