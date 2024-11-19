package com.syncbox.controllers;

import com.syncbox.models.requests.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public  String register(Model model){
        System.out.println("Register Controller");

        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);

        return "register";
    }
}
