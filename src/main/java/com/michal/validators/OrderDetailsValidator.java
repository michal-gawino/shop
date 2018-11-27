package com.michal.validators;

import com.michal.entities.OrderDetails;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class OrderDetailsValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return OrderDetails.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "street", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "postalCode", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "creditCardNumber", "field.required");
    }
}
