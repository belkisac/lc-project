package com.example.dateproject.configuration;

import com.example.dateproject.models.Category;
import com.example.dateproject.models.Product;
import com.example.dateproject.models.User;
import com.example.dateproject.models.data.CategoryDao;
import com.example.dateproject.models.data.ProductDao;
import com.example.dateproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class WebSecurity {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserService userService;

    public boolean checkCategoryId(Authentication auth, int id) {
        User user = userService.findUserByEmail(auth.getName());
        Category category = categoryDao.findOne(id);
        return category.getUser().getId() == user.getId();
    }

    public boolean checkProductId(Authentication auth, int id) {
        User user = userService.findUserByEmail(auth.getName());
        Product product = productDao.findOne(id);
        return product.getUser().getId() == user.getId();
    }

}
