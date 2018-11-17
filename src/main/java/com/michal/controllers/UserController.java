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
import java.util.List;


@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @PostMapping
    public String create(User user, Model model, RedirectAttributes redirectAttrs){
        User u = userService.findByLogin(user.getLogin());
        if(u != null){
            redirectAttrs.addFlashAttribute("user_exists", "User with given login already exists");
        }else{
            user.setPassword(encoder.encode(user.getPassword()));
            user.setRole(UserRole.CUSTOMER);
            userService.save(user);
        }
        return "redirect:/admin/users";
    }

    @DeleteMapping
    public String delete(@RequestParam(value = "usersToDelete", required = false) List<Long> ids){
        if(ids != null){
            userService.delete(ids);
        }
        return "redirect:/admin/users";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") User user, User newUser){
        if(user != null){
            if(newUser.getName() != null){
                user.setName(newUser.getName());
            }
            if(newUser.getLogin() != null){
                user.setLogin(newUser.getLogin());
            }
            if(newUser.getSurname() != null){
                user.setSurname(newUser.getSurname());
            }
            if(newUser.getPassword() != null){
                user.setPassword(encoder.encode(newUser.getPassword()));
            }
            userService.save(user);
        }
        return "redirect:/admin/users";
    }
}
