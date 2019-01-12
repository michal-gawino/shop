package com.michal.controllers;

import com.michal.entities.User;
import com.michal.impl.UserServiceImpl;
import com.michal.util.PasswordChangeForm;
import com.michal.validators.PasswordChangeValidator;
import com.michal.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;


@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private PasswordChangeValidator passwordChangeValidator;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    HttpSession session;

    @InitBinder("user")
    protected void initUserBinder(WebDataBinder binder){
        binder.addValidators(userValidator);
    }

    @InitBinder("passwordChangeForm")
    protected void initPasswordChangeBinder(WebDataBinder binder){
        binder.addValidators(passwordChangeValidator);
    }

    @GetMapping
    public String getProfileView(){
        return "profile";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String create(@Valid @ModelAttribute("u") User u, BindingResult bindingResult,
                         @RequestHeader(value = "referer", required = false) final String referrer){
        if(!bindingResult.hasFieldErrors()){
            userService.createUser(u);
        }
        return "redirect:" + referrer;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping
    public String delete(@RequestParam(value = "usersToDelete", required = false) List<Long> ids,
                         @RequestHeader(value = "referer", required = false) final String referrer){
        if(ids != null){
            userService.delete(ids);
        }
        return "redirect:" + referrer;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public String update(@PathVariable("id") User user, @ModelAttribute("newUser") User newUser,
                         @RequestHeader(value = "referer", required = false) final String referrer,
                         RedirectAttributes redirectAttrs, HttpServletRequest request){
        if(user != null){
            if(newUser.getName() != null && newUser.getName().length() > 0){
                user.setName(newUser.getName());
            }
            if(newUser.getLogin() != null){
                user.setLogin(newUser.getLogin());
            }
            if(newUser.getSurname() != null && newUser.getSurname().length() > 0){
                user.setSurname(newUser.getSurname());
            }
            if(newUser.getPassword() != null && newUser.getPassword().length() >= 6){
                user.setPassword(encoder.encode(newUser.getPassword()));
            }
            userService.save(user);
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("user.update.success",
                    null, Locale.ENGLISH));
            if(referrer.endsWith("/user")){
                session.setAttribute("user", user);
            }
        }
        return "redirect:"+ referrer;
    }

    @GetMapping("/password")
    public String changePasswordView(){
        return "change_password";
    }

    @PostMapping("/password")
    public String changePassword(@Valid @ModelAttribute("passwordChangeForm") PasswordChangeForm passwordChangeForm,
                                 BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(!bindingResult.hasFieldErrors()){
            User user = userService.getCurrent();
            user.setPassword(encoder.encode(passwordChangeForm.getNewPassword()));
            userService.save(user);
            redirectAttributes.addFlashAttribute("success",
                    messageSource.getMessage("password.change.success", null, Locale.ENGLISH));
            return "redirect:/user/password";
        }
        return "change_password";
    }



}
