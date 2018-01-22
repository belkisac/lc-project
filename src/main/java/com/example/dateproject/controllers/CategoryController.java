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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ProductDao productDao;

    //display list of all categories
    @RequestMapping(value = "")
    public String index(Model model) {
        model.addAttribute("title", "Categories");
        model.addAttribute("categories", categoryDao.findAll());
        return "category/index";
    }

    //form to add category to db (name only, no products)
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

    //view categories and all products in it
    @RequestMapping(value = "{categoryId}", method = RequestMethod.GET)
    public String viewCategory(@PathVariable int categoryId, Model model) {
        model.addAttribute("title", categoryDao.findOne(categoryId).getName());
        model.addAttribute("products", categoryDao.findOne(categoryId).getProducts());
        return "category/view";
    }

    @RequestMapping(value = "{categoryId}/add", method = RequestMethod.GET)
    public String displayAddToCategory(Model model, @PathVariable int categoryId) {
        //find products that are not already in that category and remove them
        //so user doesn't see redundant information
        ArrayList<Product> allProducts = (ArrayList<Product>) productDao.findAll();
        List<Product> categoryProducts = categoryDao.findOne(categoryId).getProducts();
        for (Product product : categoryProducts) {
            if (allProducts.contains(product)) {
                allProducts.remove(product);
            }
        }
        model.addAttribute("title", "Add Products to " + categoryDao.findOne(categoryId).getName());
        model.addAttribute(categoryDao.findOne(categoryId));
        model.addAttribute("products", allProducts);
        return "category/add-products";
    }

    @RequestMapping(value = "{categoryId}/add", method = RequestMethod.POST)
    public String processAddToCategory(Model model, int categoryId, @RequestParam int [] productIds) {
        Category thisCategory = categoryDao.findOne(categoryId);
        for (int id : productIds) {
            thisCategory.addProduct(productDao.findOne(id));
        }
        categoryDao.save(thisCategory);
        return "redirect:/category/" + thisCategory.getId();
    }

    //TODO: edit category form*/

}
