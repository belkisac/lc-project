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

@RequestMapping(value = "product")
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
        model.addAttribute(new Product());
        model.addAttribute("categories", categoryDao.findAll());
        return "product/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddForm(@ModelAttribute @Valid Product newProduct,
                                 @ModelAttribute @Valid AddProductForm newForm,
                                 @RequestParam int categoryId,
                                 Errors errors, Model model) {
        if(errors.hasErrors()) {
            model.addAttribute("form", new AddProductForm());
            model.addAttribute(new Product());
            model.addAttribute("categories", categoryDao.findAll());
            return "product/add";
        }

        Category thisCategory = categoryDao.findOne(categoryId);
        newProduct.setCategory(thisCategory);
        LocalDate entryDate = LocalDate.of(newForm.getYear(), Month.valueOf(newForm.getMonth().toUpperCase()), newForm.getDay());
        newProduct.setExpirationDate(entryDate, newProduct.getExpirationFrame());
        productDao.save(newProduct);
        return "redirect:";
    }
}
