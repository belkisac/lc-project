package com.example.dateproject.controllers;

import com.example.dateproject.models.Product;
import com.example.dateproject.models.data.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProductController {

    @Autowired
    private ProductDao productDao;


    @RequestMapping(value = "")
    public String index(Model model) {
        model.addAttribute("products", productDao.findAll());
        model.addAttribute("title", "All Products");

        return "product/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddForm(Model model) {
        model.addAttribute("title", "Add Product");
        model.addAttribute(new Product());
        return "product/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddForm() {
        return "product/index";
    }
}
