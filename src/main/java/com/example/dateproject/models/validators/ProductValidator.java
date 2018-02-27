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
        if(product.getMonth() != null && product.getDay() != null && product.getYear() != null) {
            if (product.getMonth() == 1 || product.getMonth() == 3 || product.getMonth() == 5 || product.getMonth() == 7 || product.getMonth() == 8 || product.getMonth() == 10 || product.getMonth() == 12) {
                if (product.getDay() > 31) {
                    errors.rejectValue("day", "invalid.range", "Invalid month range");
                }
            } else if (product.getMonth() == 2) {
                //leap year is valid if it's divisible by 4 and not 100 unless it is also divisible by 400
                if ((product.getYear() % 400 == 0) || (product.getYear() % 4 == 0) && (product.getYear() % 100 != 0)) {
                    if (product.getDay() > 29) {
                        errors.rejectValue("day", "invalid.range", "Invalid month range");
                    }
                } else if (product.getDay() > 28) {
                    errors.rejectValue("day", "invalid.range", "Invalid month range");
                }
            } else {
                if (product.getDay() > 30) {
                    errors.rejectValue("day", "invalid.range", "Invalid month range");
                }
            }
        }
    }

}


