package com.michal.validators;

import com.michal.entities.User;
import com.michal.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private static final int MINIMUM_PASSWORD_LENGTH = 6;

    @Autowired
    private UserServiceImpl userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field.required");
        User user = (User) o;
        if(user.getPassword().trim().length() < 6){
            errors.rejectValue("", "", "");
            errors.rejectValue("password", "field.min.length",
                    new Object[]{"Password", MINIMUM_PASSWORD_LENGTH}, "");
        }
        if(user.getLogin().trim().length() < 6){
            errors.rejectValue("", "", "");
            errors.rejectValue("login", "field.min.length",
                    new Object[]{"Login", MINIMUM_PASSWORD_LENGTH}, "");
        }
        User u = userService.findByLogin(user.getLogin());
        if(u != null){
            errors.rejectValue("login", "login.exists");
        }
    }



}
