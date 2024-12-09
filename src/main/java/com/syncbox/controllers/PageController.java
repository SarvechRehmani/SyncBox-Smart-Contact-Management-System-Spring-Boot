package com.syncbox.controllers;

import com.syncbox.helper.Message;
import com.syncbox.helper.MessageType;
import com.syncbox.models.entities.User;
import com.syncbox.models.requests.UserDto;
import com.syncbox.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class PageController {

    private final UserService userService;

    private final ModelMapper modelMapper;



    public PageController(UserService userService,ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping("/home")
    public  String index(){
        System.out.println("home Controller");
        return "redirect:/";
    }

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

    @RequestMapping("/services")
    public  String services(){
        System.out.println("About Controller");
        return "services";
    }

    @RequestMapping("/contact-us")
    public  String contactUs(){
        System.out.println("About Controller");
        return "contact";
    }



    @GetMapping("/sign-in")
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

    @PostMapping("/sign-up")
    public String register(@Valid @ModelAttribute UserDto userDto, BindingResult bindingResult, HttpSession session){
        System.out.println(userDto);
//        Validation
        if(bindingResult.hasErrors()){
            return "register";
        }

//        Converting Dto to Entity
        User user = this.modelMapper.map(userDto, User.class);
//        Save to Database
        User user2 = this.userService.saveUser(user);
        Message message = new Message("Registration Successfully", MessageType.purple);
        session.setAttribute("message",message);
        return "redirect:/sign-up";
    }
}