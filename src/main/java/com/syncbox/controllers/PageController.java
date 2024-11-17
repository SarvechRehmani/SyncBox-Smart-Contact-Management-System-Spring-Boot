package com.syncbox.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    @RequestMapping("/")
    public  String home(){
        System.out.println("Home Controller");
        return "home";
    }

    @RequestMapping("/about")
    public  String about(){
        System.out.println("About Controller");
        return "about";
    }

    @RequestMapping("/sign-in")
    public  String login(){
        System.out.println("Login Controller");
        return "login";
    }

    @RequestMapping("/sign-up")
    public  String register(){
        System.out.println("Register Controller");
        return "register";
    }
}
