package com.example.dateproject.controllers;

import com.example.dateproject.models.Product;
import com.example.dateproject.models.data.CategoryDao;
import com.example.dateproject.models.data.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CategoryDao categoryDao;

    @RequestMapping(value = "")
    public String index(Model model) {
        //gets current date and returns month value
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        List<Product> products = productDao.findByExpirationMonth(localDate.getMonthValue());

        model.addAttribute("title", "Testing");
        model.addAttribute("products", products);
        return "index/index";
    }
}
