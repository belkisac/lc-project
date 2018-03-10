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
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
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
    public String index(Model model, Authentication auth) {
        User user = userService.findUserByEmail(auth.getName());

        model.addAttribute("products", productDao.findByUserId(user.getId()));
        model.addAttribute("title", "All Products");

        return "product/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddForm(Model model, Authentication auth, HttpServletRequest request) {
        User user = userService.findUserByEmail(auth.getName());

        if(!categoryDao.findByUserId(user.getId()).isEmpty()) {
            model.addAttribute("categories", categoryDao.findByUserId(user.getId()));
        }
        model.addAttribute("referer", request.getHeader("Referer"));
        model.addAttribute(new Product());
        return "product/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddForm(@ModelAttribute @Valid Product newProduct, Errors errors,
                                 Integer categoryId, String expirationFrame,
                                 Model model, Authentication auth, String referer) {
        User user = userService.findUserByEmail(auth.getName());

        ProductValidator productValidator = new ProductValidator();
        productValidator.validate(newProduct, errors);

        if(errors.hasErrors()) {
            if(!categoryDao.findByUserId(user.getId()).isEmpty()) {
                model.addAttribute("categories", categoryDao.findByUserId(user.getId()));
            }
            return "product/add";
        }
        if(categoryId != null) {
            newProduct.setCategory(categoryDao.findOne(categoryId));
        } else {
            newProduct.setCategory(null);
        }
        newProduct.setEntryDate(newProduct.getYear(), newProduct.getMonth(), newProduct.getDay());
        newProduct.setExpirationFrame(expirationFrame);
        newProduct.setExpirationDate(newProduct.getEntryDate(), newProduct.getExpirationFrame(), newProduct.getExpirationTime());
        newProduct.setUser(user);
        productDao.save(newProduct);
        Event newEvent = new Event(newProduct.getName());
        newEvent.setStart(newProduct.getExpirationDate());
        newEvent.setUserId(Integer.toString(user.getId()));
        eventDao.save(newEvent);
        user.addProduct(newProduct);

        return "redirect:" + referer;
    }

    @RequestMapping(value = "edit/{productId}", method = RequestMethod.GET)
    public String displayEditProduct(@PathVariable int productId, Model model, Authentication auth,
                                    HttpServletRequest request) {
        User user = userService.findUserByEmail(auth.getName());

        model.addAttribute("title", "Edit " + productDao.findOne(productId).getName());
        model.addAttribute(productDao.findOne(productId));
        model.addAttribute("id", productId);
        model.addAttribute("categories", categoryDao.findByUserId(user.getId()));
        model.addAttribute("referer", request.getHeader("Referer"));
        return "product/edit";
    }

    @RequestMapping(value = "edit/{productId}", method = RequestMethod.POST)
    public String processEditProduct(@Valid @ModelAttribute("product") Product product, Errors errors,
                                     Model model, int productId, String name, Integer month, Integer year, Integer day,
                                     Long expirationTime, String expirationFrame, int categoryId,
                                     Authentication auth, String referer) {

        User user = userService.findUserByEmail(auth.getName());

        ProductValidator productValidator = new ProductValidator();
        productValidator.validate(product, errors);
        if(errors.hasErrors()) {
            model.addAttribute("title", "Edit " + productDao.findOne(productId).getName());
            model.addAttribute("product", product);
            model.addAttribute("id", productId);
            model.addAttribute("categories", categoryDao.findByUserId(user.getId()));
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

        return "redirect:" + referer;
    }

    @RequestMapping(value = "delete/{productId}", method = RequestMethod.GET)
    public String deleteProduct(@PathVariable int productId, HttpServletRequest request, Authentication auth) {
        Product toDelete = productDao.findOne(productId);
        productDao.delete(toDelete);
        Event eventDelete = eventDao.findOne(productId);
        eventDao.delete(eventDelete);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}
