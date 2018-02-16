package com.example.dateproject.models.validators;

import com.example.dateproject.models.Product;
import org.springframework.validation.Errors;

import javax.validation.Validator;

public class ProductValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {
        return Product.class.equals(clazz);
    }

    @Override
    public void validate(Product product, Errors errors) {
        if(product.getName() == null) {
            errors.rejectValue("name", "Field cannot be empty");
        }

        int[] monthsThirtyOne = new int[1,3,5,7,8,10,12];

        for(int i = 0; i < monthsThirtyOne.length; i++) {
            if(product.getMonth() == monthsThirtyOne[i]) {
                if(product.getDay() > 31) {
                    errors.rejectValue("day", "Invalid month range");
                }
            } else {
                if(product.getMonth() == 2) {
                    if(product.getYear() % 4 == 0) {
                        if(product.getYear() % 100 != 0) {
                            //divisible by 4 and NOT 100 is a leap year
                            if(product.getDay() > 29) {
                                errors.rejectValue("day", "Invalid month range");
                            }
                        }
                        if(product.getYear() % 100 == 0 && product.getYear() % 400 != 0) {
                            //divisible by 100 and NOT 400 is not a leap year
                            if(product.getDay() > 28) {
                                errors.rejectValue("day", "Invalid month range");
                            }
                        }
                        if(product.getYear() % 100 == 0 && product.getYear() % 400 == 0) {
                            //divisible by 100 AND 400 is a leap year
                            if(product.getDay() > 29) {
                                errors.rejectValue("day", "Invalid month range");
                            }
                        }
                    }

                }
                if(product.getDay() > 30) {
                    errors.rejectValue("day", "Invalid month range");
                }
            }
        }

    }

}
