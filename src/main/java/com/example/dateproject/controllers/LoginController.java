package com.example.dateproject.controllers;

import com.example.dateproject.models.Role;
import com.example.dateproject.models.User;
import com.example.dateproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String displayLoginForm(Model model) {
        model.addAttribute("title", "Login");
        return "user/login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String displayRegistrationForm(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute("user", new User());
        return "user/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processRegistrationForm(@ModelAttribute("user") @Valid User user,
                                          Errors errors, Model model) {
        User userExists = userService.findUserByEmail(user.getEmail());
        if(userExists != null) {
            errors.rejectValue("email", "error.user", "This email is already registered");
        }
        if(errors.hasErrors()) {
            return "user/register";
        } else {
            userService.saveUser(user);
            model.addAttribute("successMessage", "Registered successfully");
            model.addAttribute("title", "Register");
            model.addAttribute("user", new User());
        }
        return "user/register";
    }



}
