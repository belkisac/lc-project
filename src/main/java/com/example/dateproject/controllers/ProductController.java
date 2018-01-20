package com.example.dateproject.controllers;

import com.example.dateproject.models.Category;
import com.example.dateproject.models.Product;
import com.example.dateproject.models.data.CategoryDao;
import com.example.dateproject.models.data.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Month;

@RequestMapping(value = "product")
@Controller
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CategoryDao categoryDao;

    //display list of all products
    @RequestMapping(value = "")
    public String index(Model model) {
        model.addAttribute("products", productDao.findAll());
        model.addAttribute("title", "All Products");

        return "product/index";
    }

    //displays add product form to add one at a time
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddForm(Model model) {
        model.addAttribute("title", "Add a Product");
        model.addAttribute("categories", categoryDao.findAll());
        model.addAttribute(new Product());
        return "product/add";
    }

    //processes add form and adds product to db
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddForm(@ModelAttribute @Valid Product newProduct, Errors errors,
                                 @RequestParam int categoryId, @RequestParam String expirationFrame,
                                 Model model) {
        if(errors.hasErrors()) {
            model.addAttribute("title", "Add a Product");
            model.addAttribute("categories", categoryDao.findAll());
            return "product/add";
        }
        newProduct.setCategory(categoryDao.findOne(categoryId));
        newProduct.setEntryDate(newProduct.getYear(), newProduct.getMonth(), newProduct.getDay());
        newProduct.setExpirationFrame(expirationFrame);
        String expiration = newProduct.getExpirationFrame();
        //set expirationDate based on expirationFrame from form
        //TODO: is there a simpler way to do this?
        //TODO: explore putting this in the setter for expirationDate
        if(expiration.equals("days")) {
            newProduct.setExpirationDate(newProduct.getEntryDate().plusDays(newProduct.getExpirationTime()));
        } else if(expiration.equals("weeks")) {
            newProduct.setExpirationDate(newProduct.getEntryDate().plusWeeks(newProduct.getExpirationTime()));
        } else {
            newProduct.setExpirationDate(newProduct.getEntryDate().plusMonths(newProduct.getExpirationTime()));
        }

        productDao.save(newProduct);
        return "redirect:";
    }

    @RequestMapping(value = "edit/{productId}", method = RequestMethod.GET)
    public String displayEditProduct(@PathVariable int productId, Model model) {
        model.addAttribute("title", "Edit" + productDao.findOne(productId).getName());
        model.addAttribute(productDao.findOne(productId));
        model.addAttribute("categories", categoryDao.findAll());
        return "product/edit";
    }

    //TODO: process edit form
    @RequestMapping(value = "edit/{productId}", method = RequestMethod.POST)
    public String processEditProduct(Model model, String name)
}
