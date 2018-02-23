package com.example.dateproject.controllers;

import com.example.dateproject.models.Event;
import com.example.dateproject.models.Product;
import com.example.dateproject.models.User;
import com.example.dateproject.models.data.CategoryDao;
import com.example.dateproject.models.data.EventDao;
import com.example.dateproject.models.data.ProductDao;
import com.example.dateproject.models.data.UserDao;
import com.example.dateproject.models.validators.ProductValidator;
import com.example.dateproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Arrays;

@RequestMapping(value = "product")
@Controller
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "")
    public String index(Model model) {
        model.addAttribute("products", productDao.findAll());
        model.addAttribute("title", "All Products");

        return "product/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddForm(Model model) {
        model.addAttribute("title", "Add a Product");
        model.addAttribute("categories", categoryDao.findAll());
        model.addAttribute(new Product());
        return "product/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddForm(@ModelAttribute @Valid Product newProduct, Errors errors,
                                 int categoryId, String expirationFrame,
                                 Model model) {
        ProductValidator productValidator = new ProductValidator();
        productValidator.validate(newProduct, errors);
        if(errors.hasErrors()) {
            model.addAttribute("title", "Add a Product");
            model.addAttribute("categories", categoryDao.findAll());
            return "product/add";
        }
        newProduct.setCategory(categoryDao.findOne(categoryId));
        newProduct.setEntryDate(newProduct.getYear(), newProduct.getMonth(), newProduct.getDay());
        newProduct.setExpirationFrame(expirationFrame);
        newProduct.setExpirationDate(newProduct.getEntryDate(), newProduct.getExpirationFrame(), newProduct.getExpirationTime());
        productDao.save(newProduct);
        Event newEvent = new Event(newProduct.getName());
        newEvent.setStart(newProduct.getExpirationDate());
        eventDao.save(newEvent);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        user.setProducts(Arrays.asList(newProduct));
        return "redirect:/product";
    }

    @RequestMapping(value = "edit/{productId}", method = RequestMethod.GET)
    public String displayEditProduct(@PathVariable int productId, Model model) {
        model.addAttribute("title", "Edit " + productDao.findOne(productId).getName());
        model.addAttribute(productDao.findOne(productId));
        model.addAttribute("id", productId);
        model.addAttribute("categories", categoryDao.findAll());
        return "product/edit";
    }

    @RequestMapping(value = "edit/{productId}", method = RequestMethod.POST)
    public String processEditProduct(@Valid @ModelAttribute("product") Product product, Errors errors,
                                     Model model, int productId, String name, Integer month, Integer year, Integer day,
                                     Long expirationTime, String expirationFrame, int categoryId) {
        ProductValidator productValidator = new ProductValidator();
        productValidator.validate(product, errors);
        if(errors.hasErrors()) {
            model.addAttribute("title", "Edit " + productDao.findOne(productId).getName());
            model.addAttribute("product", product);
            model.addAttribute("id", productId);
            model.addAttribute("categories", categoryDao.findAll());
            return "product/edit";
        }

        Product editProduct = productDao.findOne(productId);
        editProduct.setName(name);
        editProduct.setMonth(month);
        editProduct.setDay(day);
        editProduct.setYear(year);
        editProduct.setExpirationTime(expirationTime);
        editProduct.setExpirationFrame(expirationFrame);
        editProduct.setEntryDate(year, month, day);
        editProduct.setExpirationDate(editProduct.getEntryDate(), expirationFrame, expirationTime);
        editProduct.setCategory(categoryDao.findOne(categoryId));
        productDao.save(editProduct);
        return "redirect:/product";
    }
}
