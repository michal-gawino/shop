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
@SessionAttributes("user")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping
    public String getProfileView(){
        return "profile";
    }

    @PostMapping
    public String create(User user, Model model, RedirectAttributes redirectAttrs,
                         @RequestHeader(value = "referer", required = false) final String referrer){
        User u = userService.findByLogin(user.getLogin());
        if(u != null){
            redirectAttrs.addFlashAttribute("user_exists", "User with given login already exists");
        }else{
            user.setPassword(encoder.encode(user.getPassword()));
            user.setRole(UserRole.CUSTOMER);
            userService.save(user);
        }
        return "redirect:" + referrer;
    }

    @DeleteMapping
    public String delete(@RequestParam(value = "usersToDelete", required = false) List<Long> ids,
                         @RequestHeader(value = "referer", required = false) final String referrer){
        if(ids != null){
            userService.delete(ids);
        }
        return "redirect:" + referrer;
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") User user, User newUser,
                         @RequestHeader(value = "referer", required = false) final String referrer,
                         RedirectAttributes redirectAttrs){
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
            redirectAttrs.addFlashAttribute("success", "Data changed successfully");
        }
        return "redirect:"+ referrer;
    }


    @GetMapping("/password")
    public String changePasswordView(){
        return "change_password";
    }

    @PostMapping("/password")
    public String changePassword(@RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("newPasswordRepeat") String newPasswordRepeat, User user, Model model){
        if(encoder.matches(currentPassword, user.getPassword())){
            if(!newPassword.equals(newPasswordRepeat)){
                model.addAttribute("error", "Given passwords do not match");
            }else{
                user.setPassword(encoder.encode(newPassword));
                userService.save(user);
                model.addAttribute("success", "Password has been changed successfully");
            }
        }else{
            model.addAttribute("error", "Current password is invalid");
        }
        return "change_password";
    }



}
