package com.example.dateproject.controllers;

import com.example.dateproject.models.Category;
import com.example.dateproject.models.data.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryDao categoryDao;

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

}
