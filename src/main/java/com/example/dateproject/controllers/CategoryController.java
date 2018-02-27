package com.example.dateproject.controllers;

import com.example.dateproject.models.Category;
import com.example.dateproject.models.Product;
import com.example.dateproject.models.User;
import com.example.dateproject.models.data.CategoryDao;
import com.example.dateproject.models.data.ProductDao;
import com.example.dateproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private UserService userService;

    //display list of all categories
    @RequestMapping(value = "")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        model.addAttribute("title", "Categories");
        model.addAttribute("categories", categoryDao.findByUserId(user.getId()));
        return "category/index";
    }

    //form to add category to db (name only, no products)
    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String displayAddCategory(Model model) {
        model.addAttribute(new Category());
        return "category/add";
    }

    @RequestMapping(value = "new", method = RequestMethod.POST)
    public String processAddCategory(Model model, @ModelAttribute @Valid Category newCategory,
                                     Errors errors) {
        //authenticate user and get user details
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        if(errors.hasErrors()) {
            return "category/add";
        }
        newCategory.setUser(user);
        user.addCategory(newCategory);
        categoryDao.save(newCategory);
        return "redirect:/category/" + newCategory.getId();
    }

    //view categories and all products in it
    @RequestMapping(value = "{categoryId}", method = RequestMethod.GET)
    public String viewCategory(@PathVariable int categoryId, Model model) {
        model.addAttribute("title", categoryDao.findOne(categoryId).getName());
        model.addAttribute("products", categoryDao.findOne(categoryId).getProducts());
        return "category/view";
    }

    //add other products to existing category
    @RequestMapping(value = "add/{categoryId}", method = RequestMethod.GET)
    public String displayAddToCategory(Model model, @PathVariable int categoryId) {
        //ensure user is only shown their own products
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        //find products that are not already in that category and remove them
        //so user doesn't see redundant information
        List<Product> allProducts = productDao.findByUserId(user.getId());
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

    @RequestMapping(value = "add/{categoryId}", method = RequestMethod.POST)
    public String processAddToCategory(Model model, int categoryId, int [] productIds) {
        Category thisCategory = categoryDao.findOne(categoryId);
        if (productIds != null){
            for (int id : productIds) {
                thisCategory.addProduct(productDao.findOne(id));
            }
        }
        categoryDao.save(thisCategory);
        return "redirect:/category/" + thisCategory.getId();
    }

    //edit category--change name or remove products
    @RequestMapping(value = "edit/{categoryId}", method = RequestMethod.GET)
    public String displayEditCategory(Model model, @PathVariable int categoryId) {
        Category thisCategory = categoryDao.findOne(categoryId);
        model.addAttribute("title", "Edit " + thisCategory.getName());
        model.addAttribute(thisCategory);
        model.addAttribute("id", thisCategory.getId());
        model.addAttribute("products", thisCategory.getProducts());
        return "category/edit";
    }

    @RequestMapping(value = "edit/{categoryId}", method = RequestMethod.POST)
    public String processEditCategory(@ModelAttribute("category") @Valid Category category, Errors errors,
                                      int categoryId, String name, int [] productIds, Model model) {
        if(errors.hasErrors()) {
            model.addAttribute("title", "Edit " + categoryDao.findOne(categoryId).getName());
            model.addAttribute("category", category);
            model.addAttribute("products", categoryDao.findOne(categoryId).getProducts());
            model.addAttribute("id", categoryId);
            return "category/edit";
        }

        Category editedCat = categoryDao.findOne(categoryId);
        editedCat.setName(name);

        if(productIds != null) {
            for (int id : productIds) {
                editedCat.removeProduct(productDao.findOne(id));
                Product removeCategory = productDao.findOne(id);
                removeCategory.setCategory(null);
            }
        }

        categoryDao.save(editedCat);
        return "redirect:/category/" + editedCat.getId();
    }
}
