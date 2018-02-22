package com.example.dateproject.controllers;

import com.example.dateproject.models.Product;
import com.example.dateproject.models.data.CategoryDao;
import com.example.dateproject.models.data.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping(value = "/")
public class IndexController {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CategoryDao categoryDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("categories", categoryDao.findAll());
        return "index/calendar";
    }
}
