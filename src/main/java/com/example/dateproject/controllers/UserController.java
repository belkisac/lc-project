package com.example.dateproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class UserController {

    @RequestMapping(value = "/login")
    public String loginPage(Model model) {
        model.addAttribute("title", "Login");
        return "login/login";
    }

}
