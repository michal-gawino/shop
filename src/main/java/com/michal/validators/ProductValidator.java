package com.michal.validators;

import com.michal.entities.Product;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ProductValidator implements Validator{

    @Override
    public boolean supports(Class<?> aClass) {
        return Product.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "brand", "field.required");
        ValidationUtils.rejectIfEmpty(errors, "price", "field.required");
        ValidationUtils.rejectIfEmpty(errors, "category", "field.required");
        Product p = (Product) o;
        if(p.getPrice() < 0){
            errors.rejectValue("price", "product.min.price");
        }
    }
}
