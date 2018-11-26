package com.michal.validators;

import com.michal.entities.User;
import com.michal.impl.UserServiceImpl;
import com.michal.util.PasswordChangeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PasswordChangeValidator implements Validator {

    private static final int MINIMUM_PASSWORD_LENGTH = 6;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserServiceImpl userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return PasswordChangeForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentPassword", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPasswordConfirmation", "field.required");
        PasswordChangeForm passwordChangeForm = (PasswordChangeForm) o;
        User user = userService.getCurrent();
        if(!encoder.matches(passwordChangeForm.getCurrentPassword(), user.getPassword())){
            errors.rejectValue("currentPassword", "password.invalid");
        }
        if(passwordChangeForm.getNewPassword().trim().length() < 6){
            errors.rejectValue("newPassword", "field.min.length", new Object[]{"Password", MINIMUM_PASSWORD_LENGTH}, "");
        }
        if(!passwordChangeForm.getNewPassword().equals(passwordChangeForm.getNewPasswordConfirmation())){
            errors.rejectValue("newPasswordConfirmation", "password.no.match");
        }
    }
}
