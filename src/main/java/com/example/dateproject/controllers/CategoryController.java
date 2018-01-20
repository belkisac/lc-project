package com.example.dateproject.controllers;

import com.example.dateproject.models.Category;
import com.example.dateproject.models.data.CategoryDao;
import com.example.dateproject.models.data.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ProductDao productDao;

    @RequestMapping(value = "")
    public String index(Model model) {
        model.addAttribute("title", "Categories");
        model.addAttribute("categories", categoryDao.findAll());
        return "category/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddCategory(Model model) {
        model.addAttribute(new Category());
        model.addAttribute("title", "Add a Category");
        return "category/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddCategory(Model model, @ModelAttribute @Valid Category newCategory,
                                     Errors errors) {
        if(errors.hasErrors()) {
            model.addAttribute("title", "Add a Category");
            return "category/add";
        }
        categoryDao.save(newCategory);
        return "redirect:";
    }

    @RequestMapping(value = "{categoryId}", method = RequestMethod.GET)
    public String viewCategory(@PathVariable int categoryId, Model model) {
        model.addAttribute("title", categoryDao.findOne(categoryId).getName());
        model.addAttribute("products", categoryDao.findOne(categoryId).getProducts());
        return "category/view";
    }

    //TODO: allow products to be added to categories in bulk
    /*@RequestMapping(value = "products", method = RequestMethod.GET)
    public String processAllProducts(Model model) {
        model.addAttribute()
    }*/

    /*@RequestMapping(value = "edit/{categoryId}", method = RequestMethod.GET)
    public String displayEditCategory(Model model, @PathVariable int categoryId) {
        model.addAttribute("title", "Edit " + categoryDao.findOne(categoryId).getName());
        model.addAttribute(categoryDao.findOne(categoryId));
        model.addAttribute("products", categoryDao.findOne(categoryId).getProducts());
        return "category/edit";
    }

    //TODO: process edit category form*/

}
