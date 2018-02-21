package com.example.dateproject.models.validators;

import com.example.dateproject.models.Product;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class ProductValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {
        return Product.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
        Product product = (Product) obj;
        if(product.getMonth() == 2 && product.getDay() > 29) {
            errors.rejectValue("day", "invalid.range", "Invalid month range");
        }
    }
}


