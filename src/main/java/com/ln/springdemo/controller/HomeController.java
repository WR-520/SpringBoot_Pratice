package com.ln.springdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/adminWelcome")
    public String adminWelcome() {
        return "page/welcome-1";
    }

    @GetMapping("/login")
    public String login(){
        return "login/login";
    }
}

