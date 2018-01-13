package com.example.dateproject.controllers;

import com.example.dateproject.models.Category;
import com.example.dateproject.models.Product;
import com.example.dateproject.models.data.CategoryDao;
import com.example.dateproject.models.data.ProductDao;
import com.example.dateproject.models.forms.AddProductForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Month;

@Controller
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CategoryDao categoryDao;


    @RequestMapping(value = "")
    public String index(Model model) {
        model.addAttribute("products", productDao.findAll());
        model.addAttribute("title", "All Products");

        return "product/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddForm(Model model) {
        model.addAttribute("title", "Add Product");
        model.addAttribute("form", new AddProductForm());
        model.addAttribute("categories", categoryDao.findAll());
        return "product/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddForm(@ModelAttribute @Valid AddProductForm form,
                                 Errors errors, @RequestParam int categoryId, Model model) {
         if (errors.hasErrors()) {
             model.addAttribute("title", "Add Product");
             return "product/add";
         }

        String name = form.getName();
        LocalDate entryDate = LocalDate.of(form.getYear(), Month.valueOf(form.getMonth().toUpperCase()), form.getDay());
        long expirationFrame = form.getExpirationTime();
        Product newProduct = new Product(name, entryDate, expirationFrame);
        newProduct.setExpirationDate(entryDate, expirationFrame);
        Category thisCategory = categoryDao.findOne(categoryId);
        newProduct.setCategory(thisCategory);
        productDao.save(newProduct);
        return "redirect:";
    }
}
