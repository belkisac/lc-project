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
        newProduct.setExpirationDate(newProduct.getEntryDate(), newProduct.getExpirationFrame(), newProduct.getExpirationTime());
        productDao.save(newProduct);
        return "redirect:";
    }

    @RequestMapping(value = "edit/{productId}", method = RequestMethod.GET)
    public String displayEditProduct(@PathVariable int productId, Model model) {
        model.addAttribute("title", "Edit " + productDao.findOne(productId).getName());
        model.addAttribute(productDao.findOne(productId));
        model.addAttribute("categories", categoryDao.findAll());
        return "product/edit";
    }

    @RequestMapping(value = "edit/{productId}", method = RequestMethod.POST)
    public String processEditProduct(Model model, int productId, String name, Integer month, Integer year, Integer day,
                                     Long expirationTime, String expirationFrame, int categoryId) {
        Product editProduct = productDao.findOne(productId);
        editProduct.setName(name);
        editProduct.setMonth(month);
        editProduct.setDay(day);
        editProduct.setYear(year);
        editProduct.setExpirationTime(expirationTime);
        editProduct.setExpirationFrame(expirationFrame);
        editProduct.setEntryDate(year, month, day);
        editProduct.setExpirationDate(editProduct.getEntryDate(), editProduct.getExpirationFrame(), editProduct.getExpirationTime());
        editProduct.setCategory(categoryDao.findOne(categoryId));
        productDao.save(editProduct);
        return "redirect:/product";
    }
}
