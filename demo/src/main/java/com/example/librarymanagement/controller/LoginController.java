package com.example.librarymanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";  // Returns the login.html template
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/login";  // Returns the login.html template
    }
}